/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.davis.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import me.davisallen.jokes.JokeGenerator;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.davis.example.com",
                ownerName = "backend.myapplication.davis.example.com",
                packagePath = ""
        )
)

public class MyEndpoint {

    /**
     * A simple endpoint method that returns a joke from the jokes library function
     */
    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        JokeGenerator jokeGenerator = new JokeGenerator();
        MyBean response = new MyBean();
        response.setData(jokeGenerator.generateJoke());

        return response;
    }

}

