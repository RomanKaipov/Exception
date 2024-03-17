import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        FileSaver saver = new FileSaver();
        saver.inputData();
        saver.save();
    }
}


class FileSaver {
    private String[] fio;
    private String birthDate;
    private Long phoneNumber;
    private String sex;

    public void inputData() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Введите ФИО через пробел, с заглавной буквы: ");
            fio = in.nextLine().split(" ");
            checkFIO(fio);
            System.out.println("Введите дату рождения в формате dd.mm.yyyy: ");
            birthDate = in.next();
            checkBirthDate(birthDate);
            System.out.println("Введите номер телефона в формате 8xxxxxxxxxx: ");
            phoneNumber = in.nextLong();
            checkPhoneNumber(phoneNumber);
            System.out.println("Введите пол (f или m): ");
            sex = in.next();
            checkSex(sex);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введён неверный разделитель даты рождения.");
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("Номер телефона требуется вводить без разделителя.");
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(fio[0] + ".txt", true)) {
            writer.write(String.format("%s %s %s %s %d %s\n", fio[0], fio[1], fio[2], birthDate, phoneNumber, sex));
        } catch (IOException e) {
            System.out.println("Произошла ошибка записи. Места ошибок: ");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private static void checkFIO(String[] fio) {
        HashSet<Integer> letters = new HashSet<>();
        for (int i = 65; i <= 90; i++) letters.add(i);
        for (int i = 1040; i <= 1071; i++) letters.add(i);

        if (fio.length != 3) throw new IllegalArgumentException("Некорректный формат ФИО.");

        if (!letters.contains(fio[0].codePointAt(0))) {
            throw new IllegalArgumentException("Первая буква фамилии должна быть заглавной");
        }

        if (!letters.contains(fio[1].codePointAt(0))) {
            throw new IllegalArgumentException("Первая буква имени должна быть заглавной");
        }

        if (!letters.contains(fio[2].codePointAt(0))) {
            throw new IllegalArgumentException("Первая буква отчества должна быть заглавной");
        }
    }

    private static void checkBirthDate(String birthDate) {
        String[] date = birthDate.split("\\.");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        if (date[0].length() != 2 || date[1].length() != 2 || date[2].length() != 4) {
            throw new IllegalArgumentException("Неверный формат даты рождения.");
        }

        if (day < 1 || day > 31 || month < 1 || month > 12) {
            throw new IllegalArgumentException("Введённая дата выходит за рамки допустимых границ.");
        }
    }

    private static void checkPhoneNumber(Long phoneNumber) {
        String phone = phoneNumber.toString();
        if (phone.charAt(0) != '8' || phone.length() != 11) {
            throw new IllegalArgumentException("Неверный формат телефона.");
        }
    }

    private static void checkSex(String sex) {
        if (!sex.equals("f") && !sex.equals("m")) {
            throw new IllegalArgumentException("Неверный формат пола.");
        }
    }

    private static boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String[] getFio() { return fio; }

    public String getBirthDate() { return birthDate; }

    public Long getPhoneNumber() { return phoneNumber; }

    public String getSex() { return sex; }

    @Override
    public String toString() {
        return "fio=" + Arrays.toString(fio) +
                ", birthDate=" + birthDate +
                ", phoneNumber=" + phoneNumber +
                ", sex=" + sex;
    }
}