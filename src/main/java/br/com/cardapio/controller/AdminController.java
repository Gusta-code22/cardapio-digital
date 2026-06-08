package br.com.cardapio.controller;

import br.com.cardapio.domain.DiaCardapio;
import br.com.cardapio.dto.DiaCardapioDTO;
import br.com.cardapio.dto.SemanaCardapioDTO;
import br.com.cardapio.service.CardapioService;
import br.com.cardapio.service.DiaCardapioService;
import br.com.cardapio.service.SemanaCardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SemanaCardapioService service;
    private final DiaCardapioService cardapioService;
    private final CardapioService cardapioHojeService;

    // DASHBOARD ADMIN
    @GetMapping
    public String painelAdmin(Model model) {
        LocalDate hoje = LocalDate.now();
        model.addAttribute("hojeFormatado", formatarData(hoje));

        Optional<DiaCardapio> cardapioHoje = cardapioHojeService.buscarCardapioDeHoje();
        cardapioHoje.ifPresent(cardapio -> {
            model.addAttribute("cardapioHoje", cardapio);
            model.addAttribute("diaHojeFormatado", traduzirDia(cardapio.getDiaSemana()));
        });

        return "admin/index";
    }

    private String formatarData(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM", new Locale("pt", "BR")));
    }

    private String traduzirDia(DayOfWeek dia) {
        return dia.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
    }

    // FORM NOVA SEMANA
    @GetMapping("/semana/novo")
    public String exibirFormulario(Model model) {
        SemanaCardapioDTO dto = new SemanaCardapioDTO();

        // Opcional: Já iniciar a lista com os 5 dias da semana preenchidos para facilitar a tela
        List<DiaCardapioDTO> dias = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dias.add(new DiaCardapioDTO());
        }
        dto.setDias(dias);

        model.addAttribute("semanaDTO", dto);
        return "admin/formulario-cardapio"; // caminho do arquivo HTML
    }

    // SALVAR SEMANA
    @PostMapping("/cardapio/novo") // <-- Alterado para corresponder ao HTML antigo
    public String salvarSemana(@ModelAttribute("semanaDTO") SemanaCardapioDTO dto) { 
        // OBS: Também alterei de "semana" para "semanaDTO" acima para bater com o th:object do HTML

        service.criarSemanaComDias(dto);

        return "redirect:/admin";
    }


    // 1. Exibe a tela com o campo para o admin selecionar a data
    @GetMapping("/dia/buscar-para-editar")
    public String exibirTelaBusca() {
        return "admin/buscar-dia"; // templates/admin/buscar-dia.html
    }

    // 2. Recebe a data do formulário de busca e redireciona para a rota correta de edição
    @PostMapping("/dia/processar-busca")
    public String processarBusca(
            @RequestParam("dataBusca") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataBusca) {

        // CORREÇÃO: Removido o "/cardapio" que causava o 404
        return "redirect:/admin/dia/editar-por-data/" + dataBusca;
    }

    // 3. Exibe o formulário de edição com os dados do dia encontrados
    @GetMapping("/dia/editar-por-data/{data}")
    public String exibirFormEdicaoDiaPorData(
            @PathVariable("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            DiaCardapio dia = cardapioService.buscarDiaPorData(data);
            model.addAttribute("diaCardapio", dia);
            return "admin/editar-dia"; // templates/admin/editar-dia.html
        } catch (IllegalArgumentException e) {
            // CORREÇÃO: Removido o "/cardapio" para redirecionar de volta à busca se der erro
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/admin/dia/buscar-para-editar";
        }
    }

    // 4. Processa a atualização salvando as alterações no banco
    @PostMapping("/dia/editar-por-data/{data}")
    public String atualizarDiaPorData(
            @PathVariable("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @ModelAttribute("diaCardapio") DiaCardapio diaAtualizado) {

        cardapioService.atualizarDiaCardapioPorData(data, diaAtualizado);
        // Redireciona para o painel principal do admin (/admin) após salvar com sucesso
        return "redirect:/admin";
    }
}