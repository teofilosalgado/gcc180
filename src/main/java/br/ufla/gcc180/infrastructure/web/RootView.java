package br.ufla.gcc180.infrastructure.web;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.theme.lumo.LumoUtility;

import br.ufla.gcc180.infrastructure.web.cliente.ClienteView;

@PageTitle("GCC180")
@RoutePrefix("")
public class RootView extends AppLayout {
    public RootView() {
        this.getStyle()
            .setHeight("100%")
            .setWidth("100%");
        
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("GCC180");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav sideNav = new SideNav();
        SideNavItem clienteSideNavItem = new SideNavItem("Clientes", ClienteView.class, VaadinIcon.USERS.create());
        sideNav.addItem(clienteSideNavItem);

        Scroller scroller = new Scroller(sideNav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title);

    }
}
