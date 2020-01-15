package com.izatec.pontointeligente.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.izatec.pontointeligente.api.dtos.FuncionarioDto;
import com.izatec.pontointeligente.api.entities.Funcionario;
import com.izatec.pontointeligente.api.enums.PerfilEnum;
import com.izatec.pontointeligente.api.response.Response;
import com.izatec.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Response<FuncionarioDto>> cadastrar(@RequestBody FuncionarioDto  funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {

        log.info("Cadastrando funcionario {}", funcionarioDto);

        Response<FuncionarioDto> response = new Response<>();

        validarDadosExistentes(funcionarioDto, result);

        Funcionario funcionario = converterDtoParaFuncionario(funcionarioDto);

        if(result.hasErrors()){
            log.error("Erro validando dados de cadastro {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterFuncionarioParaDto(funcionario));

        return ResponseEntity.ok(response);
    }

    private BindingResult  validarDadosExistentes(FuncionarioDto funcionarioDto, BindingResult result){

        Optional<Funcionario> func =  this.funcionarioService.buscarPorCpf(funcionarioDto.getCpf());

        if (func.isPresent()){
            result.addError(new ObjectError("funcionario", "funcionário já existente"));
        }

        return result;
    }

    private Funcionario converterDtoParaFuncionario(FuncionarioDto funcionarioDto){
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setEmail(funcionarioDto.getEmail());
        funcionario.setCpf(funcionarioDto.getCpf());
        funcionario.setSenha(funcionarioDto.getSenha());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setValorHora(new BigDecimal(funcionarioDto.getValorHora()));
        funcionario.setDataCriacao(new Date());
        return funcionario;
    }

    private FuncionarioDto converterFuncionarioParaDto(Funcionario funcionario){
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setNome(funcionario.getNome());
        funcionarioDto.setEmail(funcionario.getEmail());
        funcionarioDto.setCpf(funcionario.getCpf());
        funcionarioDto.setSenha(funcionario.getSenha());
        //funcionarioDto.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionarioDto.setValorHora(String.valueOf(funcionario.getValorHora()));
        return funcionarioDto;
    }
}

