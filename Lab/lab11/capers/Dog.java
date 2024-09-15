package capers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;

/** Represents a dog that can be serialized.
 * @author Sean Dooher
*/
public class Dog implements Serializable{ // FIXME

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = new File(".capers/dogs"); // FIXME

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        _age = age;
        _breed = breed;
        _name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     * If a dog with name passed in doesn't exist, throw IllegalArgumentException error.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        // FIXME //** read write */
        File dog = new File(DOG_FOLDER + "/" + name);
        if (dog.exists()) {
            String[] yoda = Utils.readContentsAsString(dog).split(":");
            return new Dog(yoda[0], yoda[1], Integer.parseInt(yoda[2]));
        }
        return null;
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        _age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        // FIXME
        try {
        HashSet<String> set = new HashSet<>();
        set.add(_name);
        set.add(":");
        set.add(_breed);
        set.add(":");
        set.add(String.valueOf(_age));
             /**Utils.writeObject(dog, set);*/
             File chowChow = new File(DOG_FOLDER + "/" + _name);
        chowChow.createNewFile();
        Utils.writeContents(chowChow, _name + ":" + _breed + ":" + _age);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            _name, _breed, _age);
    }

    /** Age of dog. */
    private int _age;
    /** Breed of dog. */
    private String _breed;
    /** Name of dog. */
    private String _name;
}
