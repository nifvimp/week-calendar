package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Categories of the Bullet Journal.
 */
public class Category {
    private final static Map<String, Category> categories = new LinkedHashMap<>();
    private final String name;

    /**
     * Makes a new category with the specified name.
     *
     * @param name name of the category
     */
    private Category(String name) {
        this.name = name;
    }

    /**
     * Gets the category object with the specified name.
     *
     * @param name name of category
     * @return the category object with the specified name
     * @throws IllegalArgumentException if a category with the specified name doesn't exist
     */
    public static Category get(String name) {
        Category category = categories.get(name);
        if (category == null) {
            throw new IllegalStateException(String.format("the category '%s' doesn't exists.", name));
        }
        return category;
    }

    /**
     * Adds a category with the specified name to the categories list.
     *
     * @param name name of category
     * @return ture if the category doesn't already exist
     */
    public static boolean add(String name) {
        return categories.putIfAbsent(name, new Category(name)) == null;
    }

    /**
     * Removes a category with the specified name to the categories list.
     *
     * @param name name of category
     * @return ture if the category was removed
     */
    public static boolean remove(String name) {
        return categories.remove(name) == null;
    }

    /**
     * Loads the json of categories into the program.
     *
     * @param json json to load
     * @param mapper object mapper to deserialize with
     */
    public static void load(JsonNode json, ObjectMapper mapper) {
        categories.clear();
        for (String category : mapper.convertValue(json, new TypeReference<List<String>>() {})) {
            add(category);
        }
    }

    /**
     * Writes the currently loaded categories to a json node.
     *
     * @param mapper object mapper to serialize with
     * @return json representation of all the registered categories
     */
    public static JsonNode write(ObjectMapper mapper) {
        return mapper.convertValue(categories.keySet(), JsonNode.class);
    }

    /**
     * Returns the name of the category.
     *
     * @return name of the category
     */
    @JsonValue
    @Override
    public String toString() {
        return this.name;
    }
}