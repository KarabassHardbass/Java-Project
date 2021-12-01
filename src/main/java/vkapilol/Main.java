package vkapilol;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ClientException, ApiException, IOException {
        Manager manager = new Manager();
        VkController controller = new VkController(
                138281156,
                "26c37c54abcbbcf5f31d759c6d66ea85103adb4f5cd213b9068b96754e083a4b00a9aa6ffafb2d996cb82",
                manager);
        controller.performCommands();
    }
}
