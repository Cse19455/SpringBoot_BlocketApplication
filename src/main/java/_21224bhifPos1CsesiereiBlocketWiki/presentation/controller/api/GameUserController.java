package _21224bhifPos1CsesiereiBlocketWiki.presentation.controller.api;


import _21224bhifPos1CsesiereiBlocketWiki.Domain.GameUser;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.Dtos.GameUserDto;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.MutateCommands.MutateUserCommand;
import _21224bhifPos1CsesiereiBlocketWiki.Services.Foundation.TemporalValueFactory;
import _21224bhifPos1CsesiereiBlocketWiki.Services.TokenService;
import _21224bhifPos1CsesiereiBlocketWiki.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping(GameUserController.BASE_URL)
public class GameUserController {

    private final UserService userService;
    @Autowired
    private TemporalValueFactory temporalValueFactory;
    @Autowired
    private TokenService tokenService;

    public static final String BASE_URL = "api/user";
    public static final String PATH_VAR_NAME ="/{name}";

    @GetMapping({"",UniversalPathVariables.PATH_INDEX})
    public HttpEntity<List<GameUser>> getUsers(){
        List<GameUser> blocks = userService.getAllUser(); //Should be be using DTOS while returning MutateCommands to the Service
        return ResponseEntity.ok(blocks);

    }

    @GetMapping({"", PATH_VAR_NAME})
    public HttpEntity<GameUser> getUserByNames(@PathVariable String firstname, String name,String username){ //Param muss String sein
        GameUser users = userService.getUserByName(new GameUserDto("",null,null,firstname,name,username,null,""));
        return ResponseEntity.ok(users);
    }

    @PostMapping({"",UniversalPathVariables.PATH_INDEX})
    public HttpEntity<GameUser> postUsers(@RequestBody MutateUserCommand mutateUserCommand){
        return ResponseEntity.ok(userService.insertUser(
                new GameUserDto(GameUser.builder()
                        .name(mutateUserCommand.getName())
                        .firstname(mutateUserCommand.getFirstname())
                        .birthDate(mutateUserCommand.getBirthDate())
                        .username(mutateUserCommand.getUsername())
                        .created_at(temporalValueFactory.create_datetimestamp())
                        .token(tokenService.createTokenFor(temporalValueFactory.create_datetimestamp()
                                ,mutateUserCommand.getFirstname()
                                ,mutateUserCommand.getName()
                                ,mutateUserCommand.getUsername()
                        ))
                        .build())));
    }
}
