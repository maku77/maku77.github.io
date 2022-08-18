import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        LinuxProcStat prev = new LinuxProcStat();

        while (true) {
            final LinuxProcStat[] stats = LinuxProcStat.getStats();
            LinuxProcStat stat = stats[0];

            long total = calcTotalTime(stat, prev);
            double rateUser = (stat.user - prev.user) * 100 / (double) total;
            double rateNice = (stat.nice - prev.nice) * 100 / (double) total;
            double rateSys = (stat.system - prev.system) * 100 / (double) total;
            double rateIdle = (stat.idle - prev.idle) * 100 / (double) total;
            System.out.format("User:%.1f%%  Nice:%.1f%%  Sys:%.1f%%  Idle:%.1f%%\n",
                    rateUser, rateNice, rateSys, rateIdle);
            prev = stat;

            // Sleep 1 seconds.
            try { Thread.sleep(1000); } catch (InterruptedException e) { /* ignore */ }
        }
    }

    private static long calcTotalTime(LinuxProcStat curr, LinuxProcStat prev) {
        return (curr.user - prev.user) +
                (curr.nice - prev.nice) +
                (curr.system - prev.system) +
                (curr.idle - prev.idle);
    }
}
