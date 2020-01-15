package com.izatec.pontointeligente.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.izatec.pontointeligente.api.dtos.CadastroPJDto;
import com.izatec.pontointeligente.api.entities.Empresa;
import com.izatec.pontointeligente.api.entities.Funcionario;
import com.izatec.pontointeligente.api.enums.PerfilEnum;
import com.izatec.pontointeligente.api.response.Response;
import com.izatec.pontointeligente.api.services.EmpresaService;
import com.izatec.pontointeligente.api.services.FuncionarioService;
import com.izatec.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin (origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    EmpresaService empresaService;

    public CadastroPJController() {
    }

    /**
     *
     * @param cadastroPJDto
     * @param result
     * @return ResponseEntity<Response<CadastroPJDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto, BindingResult result) throws NoSuchAlgorithmException {

        log.info("Cadastrando PJ {}", cadastroPJDto);

        Response<CadastroPJDto> response = new Response<CadastroPJDto>();

        validarDadosExistentes(cadastroPJDto, result);

        Empresa empresa = converterDtoParaEmpresa(cadastroPJDto);

        Funcionario funcionario = converterDtoParaFuncionario(cadastroPJDto, result);

        if(result.hasErrors()){
            log.error("Erro validando dados de cadastro PJ {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        //TODO:tratamento campos obrigatorios
        this.empresaService.persistir(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(this.conveterCadastroPjDto(funcionario));
        return ResponseEntity.ok(response);

    }

    /**
     * Verificar se empresa ou funcionario ja existem na base de dados
     *
     * @param cadastroPJDto
     * @param result
     */
    private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result){
        this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj());
                //.isPresent(emp -> result.addError(new ObjectError("empresa", "empresa já existente")));
        this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf());
        this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail());
    }

    /**
     *
     * @param cadastroPJDto
     * @return Empresa
     */
    private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto){
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
        return empresa;
    }

    /**
     *
     * @param cadastroPJDto
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
        funcionario.setQtHorasTrabalhoDia((float) 8);
        funcionario.setValorHora(BigDecimal.valueOf(105));
        return  funcionario;
    }

    /**
     *
     * @param funcionario
     * @return CadastroPJDto
     */
    private CadastroPJDto conveterCadastroPjDto(Funcionario funcionario){
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());
        return cadastroPJDto;
    }
}
