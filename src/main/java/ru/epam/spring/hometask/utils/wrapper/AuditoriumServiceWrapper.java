package ru.epam.spring.hometask.utils.wrapper;

import ru.epam.spring.hometask.service.AuditoriumService;

/**
 * Created by Dmitrii_Topolnik on 7/27/2017.
 */
public class AuditoriumServiceWrapper {
    AuditoriumService as;

    public AuditoriumServiceWrapper(AuditoriumService as) {
        this.as = as;
    }


}
