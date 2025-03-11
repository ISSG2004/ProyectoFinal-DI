package es.damdi.ismaelsg.adressappmavenjavefx.model;

import java.time.LocalDate;

import flexjson.JSON;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final IntegerProperty birthday;

    /**
     * Instantiates a new Person.
     */
    public Person() {
        this(null, null);
    }

    /**
     * Instantiates a new Person.
     *
     * @param firstName the first name
     * @param lastName  the last name
     */
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("some city");
        this.birthday = new SimpleIntegerProperty(1234);
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    @JSON
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * First name property string property.
     *
     * @return the string property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    @JSON
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Last name property string property.
     *
     * @return the string property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    @JSON
    public String getStreet() {
        return street.get();
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street.set(street);
    }

    /**
     * Street property string property.
     *
     * @return the string property
     */
    public StringProperty streetProperty() {
        return street;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    @JSON
    public int getPostalCode() {
        return postalCode.get();
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    /**
     * Postal code property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    @JSON
    public String getCity() {
        return city.get();
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city.set(city);
    }

    /**
     * City property string property.
     *
     * @return the string property
     */
    public StringProperty cityProperty() {
        return city;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    @JSON
    public int getBirthday() {
        return birthday.get();
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(int birthday) {
        this.birthday.set(birthday);
    }

    /**
     * Birthday property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty birthdayProperty() {
        return birthday;
    }
}