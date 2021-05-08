package pl.zzpj.spacer.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.zzpj.spacer.dto.AccountDto;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.mapper.AccountMapper;
import pl.zzpj.spacer.dto.mapper.NewAccountMapper;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.service.AccountServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountServiceImpl accountService;

    private final NewAccountMapper newAccountMapper;

    private final AccountMapper accountMapper;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody NewAccountDto accountDto) {
        try {
            accountService.addAccount(newAccountMapper.newAccountDtoToEntity(accountDto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("account/{username}")
    public ResponseEntity getOwnAccount(@PathVariable("username") String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(accountMapper.accountToAccountDto(accountService.getAccount(username)));
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    @GetMapping("accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAll().stream()
                .map(accountMapper::accountToAccountDto)
                .collect(Collectors.toList()));
    }

    @PutMapping("account/{username}")
    public ResponseEntity<String> editOwnAccount(@PathVariable("username") String username,
                                                 @RequestBody
                                                         NewAccountDto accountDto) {
        try {
            accountService.editAccount(username, newAccountMapper.newAccountDtoToEntity(accountDto));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}