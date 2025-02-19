package es.damdi.ismaelsg.adressappmavenjavefx.util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import es.damdi.ismaelsg.adressappmavenjavefx.model.Person;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonUtils {

    private static final String FILE_PATH = "persons.json"; // Nombre del archivo JSON

    // Serializa una lista de personas y la guarda en un archivo
    public static void savePersonsToFile(List<Person> persons) {
        String json = new JSONSerializer().prettyPrint(true).serialize(persons);

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(json);
            System.out.println("Lista de personas guardada en " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserializa una lista de personas desde un archivo JSON
    public static List<Person> loadPersonsFromFile() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new JSONDeserializer<List<Person>>().use("values", Person.class).deserialize(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
