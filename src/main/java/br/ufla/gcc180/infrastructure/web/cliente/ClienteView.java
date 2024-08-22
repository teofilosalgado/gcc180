package br.ufla.gcc180.infrastructure.web.cliente;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

import br.ufla.gcc180.application.service.ClienteService;
import br.ufla.gcc180.domain.entity.Cliente;
import br.ufla.gcc180.infrastructure.web.RootView;
import br.ufla.gcc180.infrastructure.web.cliente.fragment.ClienteRecordView;

@PageTitle("GCC180 - Cliente")
@Route(value = "cliente", layout = RootView.class)
public class ClienteView extends VerticalLayout implements AfterNavigationObserver  {

    @Autowired
    private ClienteService clienteService;

    private Grid<Cliente> grid;

    private TextField pesquisa;

    private void delete(Cliente cliente) {
        clienteService.delete(cliente);
        pesquisa.clear();

        grid.setItems(clienteService.findAll());
    }

    private void update(Cliente cliente) {
        pesquisa.clear();

        this.getUI().ifPresent(ui -> ui.navigate(ClienteRecordView.class, cliente.getId()));
    }

    private void search(ComponentValueChangeEvent<TextField, String> event) {
        if(!event.getValue().isEmpty()) {
            grid.setItems(clienteService.findAll(event.getValue()));
        }
    }
    
    
    public ClienteView() {
        this.getStyle()
                .setHeight("100%")
                .setMaxHeight("100%");

        HorizontalLayout cabecalho = new HorizontalLayout();
        cabecalho.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        cabecalho.setAlignItems(FlexComponent.Alignment.CENTER);
        cabecalho.getStyle()
                .setWidth("100%");

        H1 titulo = new H1("Clientes");
        Button criar = new Button("Criar", LumoIcon.PLUS.create());
        criar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        criar.setIconAfterText(true);
        criar.addClickListener(event -> criar.getUI().ifPresent(ui -> ui.navigate(ClienteRecordView.class)));
        criar.getStyle()
                .set("cursor", "pointer");
        cabecalho.add(titulo, criar);

        pesquisa = new TextField();
        pesquisa.setWidth("100%");
        pesquisa.setPlaceholder("Buscar por nome ou CPF");
        pesquisa.setPrefixComponent(LumoIcon.SEARCH.create());
        pesquisa.setValueChangeMode(ValueChangeMode.EAGER);
        pesquisa.setClearButtonVisible(true);
        pesquisa.addValueChangeListener(this::search);

        grid = new Grid<>(Cliente.class, false);
        grid.setAllRowsVisible(true);
        grid.addColumn(Cliente::getId).setHeader("ID");
        grid.addColumn(Cliente::getNome).setHeader("Nome");
        grid.addColumn(Cliente::getCpf).setHeader("CPF");
        grid.addColumn(Cliente::getNascimento).setHeader("Nascimento");
        grid.addColumn(Cliente::getEmail).setHeader("E-mail");
        grid.addColumn(new ComponentRenderer<>(cliente -> {
            Button apagar = new Button(LumoIcon.CROSS.create());
            apagar.getStyle()
                    .set("cursor", "pointer");
            apagar.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            apagar.addClickListener(e -> this.delete(cliente));

            Button editar = new Button(LumoIcon.EDIT.create());
            editar.getStyle()
                    .set("cursor", "pointer");
            editar.addThemeVariants(ButtonVariant.LUMO_ICON);
            editar.addClickListener(e -> this.update(cliente));

            HorizontalLayout acoes = new HorizontalLayout();
            acoes.add(editar, apagar);
            return acoes;
        })).setHeader("Ações");;
        grid.getStyle()
                .setMinHeight("100%")
                .setWidth("100%");

        Scroller scroller = new Scroller(grid);
        scroller.getStyle()
                .setHeight("100%")
                .setWidth("100%");
        
        this.add(cabecalho, pesquisa, scroller);
    }


    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        grid.setItems(clienteService.findAll());
    }
}
