package test.com.izatec.pontointeligente.api.repository;


import com.izatec.pontointeligente.api.entities.Empresa;
import com.izatec.pontointeligente.api.entities.Funcionario;
import com.izatec.pontointeligente.api.enums.PerfilEnum;
import com.izatec.pontointeligente.api.repository.EmpresaRepository;
import com.izatec.pontointeligente.api.repository.FuncionarioRepository;
import com.izatec.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String EMAIL = "email@gmail.com";

    private static final String CPF = "25888969877";

    @Before
    public void setUp() throws Exception {
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioRepository.save(obterDadosFuncionario(empresa));
    }

    @After
    public void tearDown() throws Exception {
        this.empresaRepository.deleteAll();
    }

    @Test
    @Ignore
    public void testBuscarFuncionarioPorEmail(){
        Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
        assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    @Ignore
    public void testBuscarFuncionarioPorCpf(){
        Funcionario  funcionario = this.funcionarioRepository.findByCpf(CPF);
        assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    @Ignore
    public void testBuscarFuncionarioPorEmailECpf(){
        Funcionario  funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
        assertNotNull(funcionario);
    }

    @Test
    @Ignore
    public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido(){
        Funcionario  funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "emailInvalido@gmail.com");
        assertNotNull(funcionario);
    }

    @Test
    @Ignore
    public void testBuscarFuncionarioPorEmailECpfParaEmailValido(){
        Funcionario  funcionario = this.funcionarioRepository.findByCpfOrEmail("12222333455", EMAIL);
        assertNotNull(funcionario);
    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("empresa de exemplo");
        empresa.setCnpj("51463645000100");
        return empresa;
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de Tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
        funcionario.setCpf(CPF);
        funcionario.setEmail(EMAIL);
        funcionario.setQtHorasTrabalhoDia(new Float(1));
        funcionario.setValorHora(new BigDecimal(55));
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

}
