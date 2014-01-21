package be.cegeka.retrobox.newretro;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import be.cegeka.retrobox.db.RetroRepository;
import be.cegeka.retrobox.domain.Retro;

public class NewRetroController {
    private RetroRepository retroRepository;

    public NewRetroController(RetroRepository retroRepository) {
        this.retroRepository = retroRepository;
    }

    public boolean storeRetro(String name, LocalDate date, LocalTime time, String place) {
        Retro newRetro = new Retro.Builder()
                .withLocation(place)
                .withName(name)
                .withTime(new DateTime()
                        .withDate(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth())
                        .withTime(time.getHourOfDay(), time.getMinuteOfHour(), 0, 0))
                .build();
        return retroRepository.store(newRetro);
    }
}
