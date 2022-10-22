package OkrCoreTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.Objective;

public class MainTestApplication {
  public static void main(String[] args) {
    var objective =
        Objective.of(
            "title",
            "desc",
            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - 10000,
            OkrsType.COMMIT,
            OkrsTimeType.CUSTOM,
            "userID");
    System.out.println(objective.toString());
  }
}
