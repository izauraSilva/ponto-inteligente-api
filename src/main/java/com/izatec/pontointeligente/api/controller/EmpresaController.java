package com.izatec.pontointeligente.api.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.izatec.pontointeligente.api.dtos.EmpresaDto;
import com.izatec.pontointeligente.api.entities.Empresa;
import com.izatec.pontointeligente.api.response.Response;
import com.izatec.pontointeligente.api.services.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Response<EmpresaDto>> cadastrar(@Valid @RequestBody EmpresaDto empresaDto, BindingResult result) throws NoSuchAlgorithmException {

        log.info("Cadastrando Empresa {}", empresaDto);

        Response<EmpresaDto> response = new Response<>();

        validarDadosExistentes(empresaDto, result);

        Empresa empresa = converterDtoParaEmpresa(empresaDto);

        if(result.hasErrors()){
            log.error("Erro validando dados de cadastro {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.empresaService.persistir(empresa);

        response.setData(this.conveterEmpresaDto(empresa));

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> buscaPorCnpj(@PathVariable ("cnpj") String cnpj){

        log.info("Buscando empresa por CNPJ {}", cnpj);

        Response<EmpresaDto> response = new Response<>();

        Optional<Empresa> emp =  this.empresaService.buscarPorCnpj(cnpj);

        if (!emp.isPresent()){
            log.info("Empresa não encontrada para o CNPJ {}", cnpj);
            response.getErros().add("Empresa não encontrada para o CNPJ ==> " + cnpj);
            return  ResponseEntity.badRequest().body(response);
        }

        response.setData(this.conveterEmpresaDto(emp.get()));
        return ResponseEntity.ok(response);
    }

    private BindingResult  validarDadosExistentes(EmpresaDto empresaDto, BindingResult result){

        Optional<Empresa> emp =  this.empresaService.buscarPorCnpj(empresaDto.getCnpj());

        if (emp.isPresent()){
            result.addError(new ObjectError("empresa", "empresa já existente"));
        }

        return result;
    }

    private Empresa converterDtoParaEmpresa(EmpresaDto empresaDto){
        Empresa empresa = new Empresa();
        empresa.setCnpj(empresaDto.getCnpj());
        empresa.setRazaoSocial(empresaDto.getRazaoSocial());
        return empresa;
    }

    private EmpresaDto conveterEmpresaDto(Empresa empresa){
        EmpresaDto empresaDto = new EmpresaDto();
        empresaDto.setId(empresa.getId());
        empresaDto.setCnpj(empresa.getCnpj());
        empresaDto.setRazaoSocial(empresa.getRazaoSocial());
        return empresaDto;
    }
}
