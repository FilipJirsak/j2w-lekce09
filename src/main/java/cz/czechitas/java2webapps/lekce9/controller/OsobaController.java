package cz.czechitas.java2webapps.lekce9.controller;

import cz.czechitas.java2webapps.lekce9.service.OsobaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller pro zobrazování seznamů osob.
 */
@Controller
public class OsobaController {
  private final OsobaService service;

  public OsobaController(OsobaService service) {
    this.service = service;
  }

  @GetMapping("/")
  public ModelAndView zakladniSeznam(@PageableDefault(sort = {"prijmeni", "jmeno"}) Pageable pageable) {
    return new ModelAndView("osoby")
            .addObject("osoby", service.seznamOsob(pageable));
  }

  @GetMapping("/vyber")
  public String vyber() {
    return "vyber";
  }

  /**
   * Získání aktuální URL s query parametry bez parametrů {@code size} a {@code page}.
   *
   * Je to ošklivé, ale dělá to, co potřebuju…
   */
  @ModelAttribute("currentURL")
  public String currentURL(HttpServletRequest request) {
    UrlPathHelper helper = new UrlPathHelper();
    return UriComponentsBuilder
            .newInstance()
            .path(helper.getOriginatingRequestUri(request))
            .query(helper.getOriginatingQueryString(request))
            .replaceQueryParam("size")
            .replaceQueryParam("page")
            .build()
            .toString();
  }
}
