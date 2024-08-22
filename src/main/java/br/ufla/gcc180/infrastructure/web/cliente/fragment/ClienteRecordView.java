package br.ufla.gcc180.infrastructure.web.cliente.fragment;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style.TextAlign;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

import br.ufla.gcc180.application.service.ClienteService;
import br.ufla.gcc180.domain.entity.Cliente;
import br.ufla.gcc180.infrastructure.web.RootView;
import br.ufla.gcc180.infrastructure.web.cliente.ClienteView;

@Route(value = "cliente/record", layout = RootView.class)
public class ClienteRecordView extends VerticalLayout implements HasDynamicTitle, HasUrlParameter<Long> {
    @Autowired
    private ClienteService clienteService;

    private String pageTitle = "GCC180 - Cliente";
    
    private Div titulo;
    
    private Long id;
    private TextField nome;
    private TextField cpf;
    private DatePicker nascimento;
    private TextField email;

    private void onSubmit(ClickEvent<Button> event) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome.getValue());
        cliente.setCpf(cpf.getValue());
        cliente.setNascimento(nascimento.getValue());
        cliente.setEmail(email.getValue());
        
        clienteService.save(cliente);

        this.getUI().ifPresent(ui -> ui.navigate(ClienteView.class));
    }

    public ClienteRecordView() {
        this.id = null;

        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        this.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout cabecalho = new HorizontalLayout();
        cabecalho.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        cabecalho.setAlignItems(FlexComponent.Alignment.CENTER);
        cabecalho.getStyle()
                .setWidth("100%");
        
        Button voltar = new Button("Voltar", LumoIcon.ANGLE_LEFT.create());
        voltar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        voltar.addClickListener(event -> voltar.getUI().ifPresent(ui -> ui.navigate(ClienteView.class)));
        voltar.getStyle()
                .set("cursor", "pointer");
        Div acao = new Div(voltar);
        acao.getStyle()
                .set("flex", "1");

        titulo = new Div();
        titulo.getStyle()
                .setTextAlign(TextAlign.CENTER)
                .setFontSize("var(--lumo-font-size-l)");

        Div espaco = new Div();
        espaco.getStyle()
                .set("flex", "1");

        cabecalho.add(acao, titulo, espaco);

        nome = new TextField("Nome");
        nome.setPlaceholder("José da Silva");
        cpf = new TextField("CPF");
        cpf.setPlaceholder("000.000.000-00");
        nascimento = new DatePicker ("Nascimento");
        nascimento.setPlaceholder("13/05/1966");
        email = new TextField("E-mail");
        email.setPlaceholder("jose.silva@email.com");

        
        Button confirmar = new Button("Confirmar", LumoIcon.CHECKMARK.create());
        confirmar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmar.setIconAfterText(true);
        confirmar.addClickListener(this::onSubmit);
        confirmar.getStyle()
                .setWidth("100%")
                .setMarginTop("var(--lumo-space-m)")
                .set("cursor", "pointer");

        FormLayout formulario = new FormLayout();
        formulario.setResponsiveSteps(new ResponsiveStep("0", 1));
        formulario.add(nome, cpf, nascimento, email);
        formulario.getStyle()
            .setMaxWidth("50rem");
        Div corpo = new Div(formulario, confirmar);
        
        this.add(cabecalho, corpo);
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
        if(parameter == null) {
            this.id = null;
            this.pageTitle = "GCC180 - Cliente - Criar";
            titulo.setText("Criar cliente");
        } else {
            this.id = parameter;
            this.pageTitle = String.format("GCC180 - Cliente - Editar (%s)", parameter);
            titulo.setText(String.format("Editar cliente (ID %s)", parameter));

            Optional<Cliente> cliente = clienteService.findById(parameter);
            if(cliente.isPresent()) {
                this.nome.setValue(cliente.get().getNome());
                this.cpf.setValue(cliente.get().getCpf());
                this.nascimento.setValue(cliente.get().getNascimento());
                this.email.setValue(cliente.get().getEmail());
            }
        }
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }
}
