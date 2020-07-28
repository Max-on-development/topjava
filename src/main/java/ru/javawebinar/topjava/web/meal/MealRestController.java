package ru.javawebinar.topjava.web.meal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "MealRestController текст";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal createdMeal = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(createdMeal.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdMeal);
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @GetMapping("/filter")
    public List<MealTo> getBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return super.getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(), endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }

    @GetMapping("/filterWithNull")
    public List<MealTo> getBetween(HttpServletRequest request) {
        LocalDate startDate = new StringToLocalDate().convert(request.getParameter("startDate"));
        LocalDate endDate = new StringToLocalDate().convert(request.getParameter("endDate"));
        LocalTime startTime = new StringToLocalTime().convert(request.getParameter("startTime"));
        LocalTime endTime = new StringToLocalTime().convert(request.getParameter("endTime"));
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

/*    @GetMapping("/filterWithNull")
    public List<MealTo> getBetweenUpdated(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                          @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                          @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                          @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }*/

    final class StringToLocalDate implements Converter<String, LocalDate> {

        @Nullable
        public LocalDate convert(String source) {
            return source.length() > 0 ? LocalDate.of(Integer.parseInt(source.substring(0, 4)),
                    Integer.parseInt(source.substring(5, 7)),
                    Integer.parseInt(source.substring(8))) : null;
        }
    }

    final class StringToLocalTime implements Converter<String, LocalTime> {

        @Nullable
        public LocalTime convert(String source) {
            return source.length()>0 ? LocalTime.of(Integer.parseInt(source.substring(0, 2)),
                    Integer.parseInt(source.substring(3))) : null;
        }
    }
}
