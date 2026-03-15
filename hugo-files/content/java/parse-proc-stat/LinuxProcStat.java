import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LinuxProcStat {
    private static String STAT_PATH = "/proc/stat";

    public String name = "";
    public long user = 0;
    public long nice = 0;
    public long system = 0;
    public long idle = 0;
    public long iowait = 0;
    public long irq = 0;
    public long softirq = 0;
    public long steal = 0;
    public long guest = 0;
    public long guest_nice = 0;  // kernel 2.6.33 or upper

    public static LinuxProcStat[] getStats() {
        ArrayList<LinuxProcStat> stats = new ArrayList<LinuxProcStat>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(STAT_PATH));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (! isCpuLine(line)) break;
                stats.add(createStat(line));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (br != null) try { br.close(); } catch (IOException e) {}
        }
        return stats.toArray(new LinuxProcStat[0]);
    }

    private static boolean isCpuLine(String line) {
        return line.startsWith("cpu");
    }

    private static LinuxProcStat createStat(String line) {
        LinuxProcStat stat = new LinuxProcStat();
        final String[] items = line.split("\\s+");

        int n = items.length;
        if (n >= 1) stat.name = items[0].trim();
        if (n >= 2) stat.user = Long.parseLong(items[1].trim());
        if (n >= 3) stat.nice = Long.parseLong(items[2].trim());
        if (n >= 4) stat.system = Long.parseLong(items[3].trim());
        if (n >= 5) stat.idle = Long.parseLong(items[4].trim());
        if (n >= 6) stat.iowait = Long.parseLong(items[5].trim());
        if (n >= 7) stat.irq = Long.parseLong(items[6].trim());
        if (n >= 8) stat.softirq = Long.parseLong(items[7].trim());
        if (n >= 9) stat.steal = Long.parseLong(items[8].trim());
        if (n >= 10) stat.guest = Long.parseLong(items[9].trim());
        if (n >= 11) stat.guest_nice = Long.parseLong(items[10].trim());

        return stat;
    }
}
