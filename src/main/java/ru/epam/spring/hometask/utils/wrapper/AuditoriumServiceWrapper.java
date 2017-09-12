package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.domain.Auditorium;
import ru.epam.spring.hometask.service.AuditoriumService;

import java.util.HashSet;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class AuditoriumServiceWrapper {
    AuditoriumService as;

    public AuditoriumServiceWrapper(AuditoriumService as) {
        this.as = as;
    }


    public String getAll() {
        HashSet<Auditorium> ads = (HashSet<Auditorium>) as.getAll();
        StringBuffer sb = new StringBuffer();
        for (Auditorium ad : ads ){
            sb.append(ad.toString() + "\n");
        }
        return sb.toString();

    }

    public String getByName(String name) {
        Auditorium ad = as.getByName(name);
        return (ad==null)?"not found":ad.toString();

    }
}
