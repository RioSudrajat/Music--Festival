package main.model;

/**
* Mewakili manager dengan kemampuan untuk menambah, memperbarui, dan menghapus festival di Aplikasi Festival Musik.
 * Mewarisi dari kelas Karyawan, memanfaatkan atribut pengguna bersama dan menambahkan fungsi khusus manager.
 */
public class Manager extends Employee {

    public Manager(String email) {
        super(email);
    }

    public Manager(String email, String password) {
        super(email, password);
    }

    public Manager(String firstName, String lastName, String login, String password,
            boolean isEmployee, boolean isManager) {
        super(firstName, lastName, login, password, isEmployee, isManager);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Manager)) return false;
        Manager that = (Manager) other;
        return this.email.equals(that.email);
    }
}