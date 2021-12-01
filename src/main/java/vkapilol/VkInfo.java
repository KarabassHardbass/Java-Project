package vkapilol;

public class VkInfo {
    private long vkId = 0;
    private String name = "имя не указано";
    private String surname = "фамилия не указана";
    private String phone = "номер телефона не указан";

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String toString() {
        return "ID: " + vkId + ", Имя: " + name + ", Фамилия: " + surname + ", Номер телефона: " + phone;
    }
    public VkInfo(String[] userValues) {
        for (var userValue : userValues) {
            var parts = userValue.split(":");
            var value = parts[1].replace("\"", "");
            if (parts[0].equals("\"first_name\"")) {
                name = value;
            } else if (parts[0].equals("\"last_name\"")) {
                surname = value;
            } else if (parts[0].equals("\"mobile_phone\"")) {
                phone = value;
            } else if (parts[0].equals("\"id\"")) {
                vkId = Integer.parseInt(value);
            }
        }
    }

    public VkInfo() {

    }
}
