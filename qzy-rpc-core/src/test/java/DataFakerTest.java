import net.datafaker.Faker;
import net.datafaker.providers.entertainment.ResidentEvil;
import net.datafaker.providers.videogame.DarkSouls;
import net.datafaker.providers.videogame.EldenRing;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author qzy
 * @time 2025年1月03日 11:26 星期五
 * @title
 */
public class DataFakerTest {
    @Test
    public void quickStart() {
        Faker faker = new Faker(new Locale("zh-CN"));
        String name = faker.name().fullName();
        System.out.println("name = " + name);


        EldenRing eldenRing = faker.eldenRing();
        System.out.println("eldenRing.npc() = " + eldenRing.npc());
        System.out.println("eldenRing.location() = " + eldenRing.location());
        System.out.println("eldenRing.skill() = " + eldenRing.skill());
        System.out.println("eldenRing.spell() = " + eldenRing.spell());
        System.out.println("eldenRing.weapon() = " + eldenRing.weapon());
        DarkSouls darkSouls = faker.darkSouls();
        System.out.println("darkSouls.covenants() = " + darkSouls.covenants());
        System.out.println("darkSouls.shield() = " + darkSouls.shield());
        System.out.println("darkSouls.stats() = " + darkSouls.stats());
    }
}
