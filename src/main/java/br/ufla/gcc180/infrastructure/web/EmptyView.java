package br.ufla.gcc180.infrastructure.web;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("GCC180")
@Route(value = "", layout = RootView.class)
public class EmptyView extends VerticalLayout{
    public EmptyView() {
        this.getStyle()
                .setHeight("100%")
                .setWidth("100%")
                .setBackgroundColor("var(--lumo-contrast-10pct)");
    }
}
