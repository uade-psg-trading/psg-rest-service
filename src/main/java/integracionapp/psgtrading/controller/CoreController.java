package integracionapp.psgtrading.controller;

import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.service.CoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/core")
public class CoreController {

    private CoreService coreService;

    @PostMapping("")
    public ResponseEntity<GenericDTO<String>> createMessage(@RequestBody String message){
        try{
            coreService.sendMessage(message);
            return new ResponseEntity<>(GenericDTO.success(message), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"There was a problem", ex);
        }

    }
}
