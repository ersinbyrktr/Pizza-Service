package rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class GreetingController {

    @RequestMapping(value = "/greeting/{greetingName:[a-zA-Z]+}", method=GET)
    public String greeting(@PathVariable("greetingName") final String greetingName,
                           @RequestParam(value = "userId", required = false) final Integer userId,
                           final NativeWebRequest request) {
        return "greetings " + greetingName;
    }

}