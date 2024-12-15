package main.model;


/**
 * Kelas abstrak untuk mewakili pengguna di Aplikasi Festival Musik.
 * Pengguna dapat berupa pelanggan atau karyawan, dengan atribut dasar seperti nama, email, dan kata sandi.
 */
public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    public String password;
    protected boolean isEmployee;

    public User(String email) {
        this.firstName = "nama depan tidak diketahui";
        this.lastName = "nama belakang tidak diketahui";
        this.email = email;
        this.password = "password tidak diketahui";
        this.isEmployee = false;
    }

    public User(String email, String password) {
        this.firstName = "nama depan tidak diketahui";
        this.lastName = "nama belakang tidak diketahui";
        this.email = email;
        this.password = password;
        this.isEmployee = false;
    }

    public User(String firstName, String lastName, String email, String password, boolean isEmployee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isEmployee = isEmployee;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public boolean passwordMatch(String anotherPassword) {
        return this.password.equals(anotherPassword);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            return hashCode() == obj.hashCode();
        }
    }

    @Override
    public int hashCode() {
        return Math.abs(email.hashCode());
    }
}
