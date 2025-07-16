package com.themadstatter.pathfinder.measure;

import com.google.gson.*;

import java.lang.reflect.Type;

public class SettingsDeserializer implements JsonDeserializer<Settings> {
    @Override
    public Settings deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        Settings settings;
        settings = new Settings();

        Gson gson = new Gson();

        settings.appTitle = obj.has("appTitle") ? obj.get("appTitle").getAsString() : null;
        settings.width = obj.has("width") ? obj.get("width").getAsInt() : 800;
        settings.height = obj.has("height") ? obj.get("height").getAsInt() : 600;
        settings.decimals = obj.has("decimals") ? obj.get("decimals").getAsInt() : 0;
        settings.min = obj.has("min") ? obj.get("min").getAsInt() : 1;
        settings.max = obj.has("max") ? obj.get("max").getAsInt() : 9;
        settings.terms = obj.has("terms") ? gson.fromJson(obj.getAsJsonArray("terms"), String[].class) : new String[]{"apple", "orange", "banana"};
        settings.instructions = obj.has("instructions") ? gson.fromJson(obj.getAsJsonArray("instructions"), String[].class) : new String[]{"Failed to parse \"instructions\""};
        settings.debriefing = obj.has("debriefing") ? gson.fromJson(obj.getAsJsonArray("debriefing"), String[].class) : new String[]{"Failed to parse \"debriefing\""};

        // Slider
        settings.majorTickSpacing = obj.has("majorTickSpacing") ? obj.get("majorTickSpacing").getAsInt() : 1;
        settings.minorTickSpacing = obj.has("minorTickSpacing") ? obj.get("minorTickSpacing").getAsInt() : 1;
        settings.paintTicks = !obj.has("paintTicks") || obj.get("paintTicks").getAsBoolean();
        settings.paintLabels = !obj.has("paintLabels") || obj.get("paintLabels").getAsBoolean();

        return settings;
    }
}
