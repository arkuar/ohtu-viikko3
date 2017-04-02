package ohtu.data_access;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;
import org.springframework.stereotype.Component;

@Component
public class FileUserDao implements UserDao {

    File file;
    List<User> users;

    public FileUserDao(String file) {
        this.file = new File(file);
    }

    @Override
    public List<User> listAll() {
        users = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String username = scanner.next();
                String password = scanner.next();
                users.add(new User(username, password));
            }
        } catch (FileNotFoundException e) {
        }
        return users;
    }

    @Override
    public User findByName(String name) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String username = scanner.next();
                String password = scanner.next();
                if (username.equals(name)) {
                    return new User(username, password);
                }
            }
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            users = listAll();
            users.add(user);
            FileWriter writer = new FileWriter(file);
            for (User a : users) {
                writer.write(a.getUsername() + " " + a.getPassword() + "\n");
            }
            writer.close();

        } catch (IOException e) {
        }
    }
}
