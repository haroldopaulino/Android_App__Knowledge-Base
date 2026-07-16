package com.prpinfo.bancodesolucoes.wearable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {
    private static final String ENDPOINT = "https://haroldopaulino.com/web/knowledge_base/gateway.php";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private SharedPreferences preferences;
    private final List<Solution> solutions = new ArrayList<>();
    private JSONArray categories = new JSONArray();
    private LinearLayout content;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("knowledge_base", MODE_PRIVATE);
        loadCachedData();
        if (preferences.getString("company_id", "").isEmpty()) showLogin(); else showHome();
    }

    @Override
    protected void onDestroy() {
        executor.shutdownNow();
        super.onDestroy();
    }

    private void shell(String title) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(dp(14), dp(12), dp(14), dp(18));
        TextView heading = text(title, 20, true);
        heading.setGravity(Gravity.CENTER);
        heading.setPadding(0, dp(4), 0, dp(10));
        content.addView(heading, matchWrap());
        progress = new ProgressBar(this);
        progress.setVisibility(View.GONE);
        content.addView(progress, wrapWrap());
        scroll.addView(content);
        setContentView(scroll);
    }

    private void showLogin() {
        shell("Knowledge Base");
        EditText company = input("Company", false);
        EditText email = input("Email", false);
        email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        EditText password = input("Password", true);
        company.setText(preferences.getString("company", ""));
        email.setText(preferences.getString("email", ""));
        add(company); add(email); add(password);
        Button login = button("Sign in");
        login.setOnClickListener(v -> login(company, email, password));
        add(login);
        TextView note = text("Sign in with the same account used by the phone app.", 12, false);
        note.setGravity(Gravity.CENTER);
        note.setPadding(dp(6), dp(8), dp(6), 0);
        add(note);
    }

    private void login(EditText company, EditText email, EditText password) {
        String c = company.getText().toString().trim();
        String e = email.getText().toString().trim();
        String p = password.getText().toString();
        if (c.isEmpty() || e.isEmpty() || p.isEmpty()) {
            toast("Enter company, email and password");
            return;
        }
        preferences.edit().putString("company", c).putString("email", e).putString("password", p).apply();
        setBusy(true);
        executor.execute(() -> {
            try {
                JSONObject response = request(new JSONObject().put("action", "LOGIN").put("login_attempt", "1"), false);
                if (!response.has("company_id") || !response.has("categories_data")) throw new Exception();
                preferences.edit()
                        .putString("company_id", response.optString("company_id"))
                        .putString("company_description", response.optString("company_description", c))
                        .putString("firstname", response.optString("firstname"))
                        .putString("lastname", response.optString("lastname"))
                        .putString("access_type", response.optString("access_type"))
                        .putString("categories", response.getJSONArray("categories_data").toString())
                        .apply();
                categories = response.getJSONArray("categories_data");
                runOnUiThread(() -> refreshSolutions(true));
            } catch (Exception ex) {
                runOnUiThread(() -> { setBusy(false); toast("Unable to sign in"); });
            }
        });
    }

    private void showHome() {
        shell("Knowledge Base");
        String name = (preferences.getString("firstname", "") + " " + preferences.getString("lastname", "")).trim();
        if (!name.isEmpty()) {
            TextView welcome = text("Welcome, " + name, 14, false);
            welcome.setGravity(Gravity.CENTER);
            welcome.setPadding(0, 0, 0, dp(8));
            add(welcome);
        }
        Button search = button("Search solutions");
        search.setOnClickListener(v -> showSearch());
        add(search);
        Button categoriesButton = button("Browse categories");
        categoriesButton.setOnClickListener(v -> showCategories());
        add(categoriesButton);
        Button all = button("All approved solutions");
        all.setOnClickListener(v -> showResults("All solutions", approvedSolutions()));
        add(all);
        Button refresh = button("Sync latest data");
        refresh.setOnClickListener(v -> refreshSolutions(false));
        add(refresh);
        Button account = button("Account");
        account.setOnClickListener(v -> showAccount());
        add(account);
        TextView count = text(solutions.size() + " solutions available offline", 12, false);
        count.setGravity(Gravity.CENTER);
        count.setPadding(0, dp(8), 0, 0);
        add(count);
    }

    private void showSearch() {
        shell("Search");
        add(backButton(this::showHome));
        EditText query = input("Problem or solution", false);
        query.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        add(query);
        Button search = button("Search");
        View.OnClickListener action = v -> {
            String term = query.getText().toString().trim().toLowerCase(Locale.ROOT);
            List<Solution> result = new ArrayList<>();
            for (Solution item : solutions) {
                if (item.approved.equalsIgnoreCase("Y") && (term.isEmpty() || item.problem.toLowerCase(Locale.ROOT).contains(term) || item.solution.toLowerCase(Locale.ROOT).contains(term) || item.category.toLowerCase(Locale.ROOT).contains(term))) result.add(item);
            }
            showResults("Search results", result);
        };
        search.setOnClickListener(action);
        query.setOnEditorActionListener((v, actionId, event) -> { if (actionId == EditorInfo.IME_ACTION_SEARCH) { action.onClick(v); return true; } return false; });
        add(search);
    }

    private void showCategories() {
        shell("Categories");
        add(backButton(this::showHome));
        for (int i = 0; i < categories.length(); i++) {
            JSONObject category = categories.optJSONObject(i);
            if (category == null) continue;
            String id = category.optString("id");
            String description = category.optString("description", category.optString("category_description", "Category"));
            if (description.equalsIgnoreCase("all") || id.equals("1")) continue;
            Button item = button(description);
            item.setOnClickListener(v -> {
                List<Solution> filtered = new ArrayList<>();
                for (Solution solution : solutions) if (solution.approved.equalsIgnoreCase("Y") && String.valueOf(solution.categoryId).equals(id)) filtered.add(solution);
                showResults(description, filtered);
            });
            add(item);
        }
    }

    private void showResults(String title, List<Solution> result) {
        shell(title);
        add(backButton(this::showHome));
        TextView count = text(result.size() + " items", 12, false);
        count.setGravity(Gravity.CENTER);
        add(count);
        for (Solution solution : result) {
            Button item = button(solution.problem);
            item.setOnClickListener(v -> showDetail(solution));
            add(item);
        }
        if (result.isEmpty()) {
            TextView empty = text("No matching solutions", 14, false);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(0, dp(16), 0, 0);
            add(empty);
        }
    }

    private void showDetail(Solution solution) {
        shell("Solution");
        add(backButton(this::showHome));
        add(label("Category", solution.category));
        add(label("Problem", solution.problem));
        add(label("Solution", solution.solution));
    }

    private void showAccount() {
        shell("Account");
        add(backButton(this::showHome));
        add(label("Name", (preferences.getString("firstname", "") + " " + preferences.getString("lastname", "")).trim()));
        add(label("Company", preferences.getString("company_description", preferences.getString("company", ""))));
        add(label("Email", preferences.getString("email", "")));
        add(label("Access", preferences.getString("access_type", "")));
        Button logout = button("Sign out");
        logout.setOnClickListener(v -> new AlertDialog.Builder(this).setTitle("Sign out").setMessage("Remove the account from this watch?").setNegativeButton("Cancel", null).setPositiveButton("Sign out", (d, w) -> {
            preferences.edit().clear().apply();
            solutions.clear();
            categories = new JSONArray();
            showLogin();
        }).show());
        add(logout);
    }

    private void refreshSolutions(boolean afterLogin) {
        setBusy(true);
        executor.execute(() -> {
            try {
                String raw = requestRaw(new JSONObject().put("action", "GET_SOLUTIONS_LOOKUP_LIST").put("category_id", "").put("problem", "").put("solution", "").put("approved", ""));
                List<Solution> downloaded = parseSolutions(raw);
                if (downloaded.isEmpty()) throw new Exception();
                solutions.clear();
                solutions.addAll(downloaded);
                preferences.edit().putString("solutions", raw).apply();
                runOnUiThread(() -> { setBusy(false); toast("Synced " + solutions.size() + " solutions"); showHome(); });
            } catch (Exception ex) {
                runOnUiThread(() -> { setBusy(false); if (afterLogin || solutions.isEmpty()) toast("Signed in, but sync failed"); showHome(); });
            }
        });
    }

    private JSONObject request(JSONObject payload, boolean raw) throws Exception {
        return decodeResponse(requestRaw(payload));
    }

    private String requestRaw(JSONObject payload) throws Exception {
        JSONObject data = credentials(payload);
        JSONObject encoded = new JSONObject();
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            encoded.put(key, Base64.encodeToString(data.optString(key).getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
        }
        String params = "params=" + URLEncoder.encode(Base64.encodeToString(encoded.toString().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT), StandardCharsets.UTF_8.name());
        HttpURLConnection connection = (HttpURLConnection) new URL(ENDPOINT).openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(20000);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (DataOutputStream output = new DataOutputStream(connection.getOutputStream())) { output.write(params.getBytes(StandardCharsets.UTF_8)); }
        if (connection.getResponseCode() != 200) throw new Exception();
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
        }
        return response.toString();
    }

    private JSONObject credentials(JSONObject payload) throws Exception {
        payload.put("company_id", preferences.getString("company_id", ""));
        payload.put("company", preferences.getString("company", ""));
        payload.put("email", preferences.getString("email", ""));
        payload.put("password", preferences.getString("password", ""));
        payload.put("client_datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()));
        return payload;
    }

    private JSONObject decodeResponse(String raw) throws Exception {
        JSONObject source = new JSONObject(raw);
        JSONObject decoded = new JSONObject();
        Iterator<String> keys = source.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = source.optString(key);
            if (key.equals("categories_data") || key.equals("users_data")) {
                JSONArray array = new JSONArray(value);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    Iterator<String> itemKeys = item.keys();
                    while (itemKeys.hasNext()) {
                        String itemKey = itemKeys.next();
                        item.put(itemKey, decode(item.optString(itemKey)));
                    }
                }
                decoded.put(key, array);
            } else if (key.equals("user_data") || key.equals("languages") || key.equals("access_types")) {
                JSONObject object = new JSONObject(value);
                Iterator<String> objectKeys = object.keys();
                while (objectKeys.hasNext()) {
                    String objectKey = objectKeys.next();
                    object.put(objectKey, decode(object.optString(objectKey)));
                }
                decoded.put(key, object);
            } else decoded.put(key, decode(value));
        }
        return decoded;
    }

    private List<Solution> parseSolutions(String raw) {
        List<Solution> output = new ArrayList<>();
        String[] records = raw.replace(',', '\n').split("\\r?\\n");
        for (String record : records) {
            try {
                JSONObject object = new JSONObject(decode(record.trim()));
                output.add(new Solution(object.optInt("category_id"), object.optString("category_description"), object.optString("problem"), object.optString("solution"), object.optString("approved")));
            } catch (Exception ignored) { }
        }
        return output;
    }

    private void loadCachedData() {
        try { categories = new JSONArray(preferences.getString("categories", "[]")); } catch (Exception ignored) { categories = new JSONArray(); }
        solutions.clear();
        solutions.addAll(parseSolutions(preferences.getString("solutions", "")));
    }

    private List<Solution> approvedSolutions() {
        List<Solution> output = new ArrayList<>();
        for (Solution solution : solutions) if (solution.approved.equalsIgnoreCase("Y")) output.add(solution);
        return output;
    }

    private String decode(String value) {
        try { return new String(Base64.decode(value, Base64.DEFAULT), StandardCharsets.UTF_8); } catch (Exception ignored) { return value; }
    }

    private TextView label(String title, String value) {
        TextView view = text(title + "\n" + value, 14, false);
        view.setPadding(dp(10), dp(10), dp(10), dp(10));
        view.setBackgroundColor(Color.rgb(28, 28, 30));
        LinearLayout.LayoutParams params = matchWrap();
        params.setMargins(0, dp(4), 0, dp(4));
        view.setLayoutParams(params);
        return view;
    }

    private EditText input(String hint, boolean password) {
        EditText input = new EditText(this);
        input.setHint(hint);
        input.setTextColor(Color.WHITE);
        input.setHintTextColor(Color.LTGRAY);
        input.setSingleLine(true);
        input.setTextSize(14);
        input.setInputType(password ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
        input.setLayoutParams(matchWrap());
        return input;
    }

    private Button button(String title) {
        Button button = new Button(this);
        button.setText(title);
        button.setAllCaps(false);
        button.setTextSize(13);
        LinearLayout.LayoutParams params = matchWrap();
        params.setMargins(0, dp(3), 0, dp(3));
        button.setLayoutParams(params);
        return button;
    }

    private Button backButton(Runnable action) {
        Button back = button("‹ Back");
        back.setOnClickListener(v -> action.run());
        return back;
    }

    private TextView text(String value, int size, boolean bold) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextColor(Color.WHITE);
        view.setTextSize(size);
        if (bold) view.setTypeface(view.getTypeface(), android.graphics.Typeface.BOLD);
        return view;
    }

    private void add(View view) { content.addView(view); }
    private void setBusy(boolean busy) { if (progress != null) progress.setVisibility(busy ? View.VISIBLE : View.GONE); }
    private void toast(String text) { Toast.makeText(this, text, Toast.LENGTH_SHORT).show(); }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
    private LinearLayout.LayoutParams matchWrap() { return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); }
    private LinearLayout.LayoutParams wrapWrap() { return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); }

    private static class Solution {
        final int categoryId;
        final String category;
        final String problem;
        final String solution;
        final String approved;
        Solution(int categoryId, String category, String problem, String solution, String approved) {
            this.categoryId = categoryId;
            this.category = category;
            this.problem = problem;
            this.solution = solution;
            this.approved = approved;
        }
    }
}
