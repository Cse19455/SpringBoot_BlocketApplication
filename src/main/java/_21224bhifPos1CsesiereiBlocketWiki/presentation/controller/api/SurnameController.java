package _21224bhifPos1CsesiereiBlocketWiki.presentation.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping(SurnameController.BASE_URL)
public class SurnameController {
    public static final String BASE_URL = "/surname";
}
