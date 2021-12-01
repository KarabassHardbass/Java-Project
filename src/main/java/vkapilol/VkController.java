package vkapilol;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.*;

import java.util.Scanner;

public class VkController {
    private Scanner scanner = new Scanner(System.in);
    private int id;
    private String token;
    private Manager manager;
    private boolean managerOpened = false;
    private List<VkInfo> users = new ArrayList<>();

    public VkController(int id, String token, Manager manager) throws ClientException, ApiException {
        this.id = id;
        this.token = token;
        this.manager = manager;
    }

    public void addUsers() throws ClientException {
        var transportClient = new HttpTransportClient();
        var vkApiClient = new VkApiClient(transportClient);
        var userActor = new UserActor(id, token);
        var fields = new Fields[] { Fields.ABOUT, Fields.CONTACTS };
        var content = vkApiClient.groups().getMembers(userActor).groupId("208878787").fields(fields).executeAsString();
        System.out.println(content);
        parseInfo(content);
    }

    public void parseInfo(String data) {
        var usersData = data.split("\\[")[1].split("\\]")[0].replace("{", "");
        var usersInfo = usersData.replace("},", "}").split("}");
        for (var userInfo : usersInfo) {
            var userValues = userInfo.split(",");
            users.add(new VkInfo(userValues));
        }
    }

    public void openManager() throws IOException {
        if (!managerOpened) {
            manager.parseFromFile();
            managerOpened = true;
        }
        manager.performCommands();
    }

    public void printUser(int id) {
        System.out.println(users.get(id).toString());
    }

    public void addInfo(int id, int idInGroup) {
        manager.getStudent(id).setVkInfo(users.get(idInGroup));
    }

    public void performCommands() throws ClientException, ApiException, IOException {
        while (true) {
            System.out.println("Введите команду (help для помощи):");
            String[] input = scanner.nextLine().split(" ");
            if (input[0].equals("Stop")) {
                System.out.println("Выполнение программы принудительно остановлено.");
                break;
            }
            if (input[0].equals("AddUsers")) {
                addUsers();
            }
            if (input[0].equals("OpenManager")) {
                System.out.println("Сейчас используется CSV Parser.");
                openManager();
                System.out.println("Сейчас используется Vk Parser.");
            }
            if (input[0].equals("PrintUser")) {
                printUser(Integer.parseInt(input[1]));
            }
            if (input[0].equals("AddInfo")) {
                addInfo(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
            }
            if (input[0].equals("help")) {
                System.out.println("Stop - остановить выполнение программы.");
                System.out.println("AddUsers - добавить пользователей группы.");
                System.out.println("OpenManager - открыть CSV Parser.");
                System.out.println("AddInfo <id пользователя> <id пользователя в группе> - добавить информацию пользователю.");
                System.out.println("PrintUser <id пользователя в группе> - вывести информацию о пользователе группы.");
            }
        }
    }
}
