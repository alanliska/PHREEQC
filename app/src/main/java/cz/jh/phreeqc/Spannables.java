package cz.jh.phreeqc;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

// the last colorization within the function is the most important (will be applied as the last)
// rule: the first are the least important colorizations, overriden by the last ones

public class Spannables extends MainActivity {

    public static Spannable colorized_numbers(final String text) {
        SpannableString spannable = new SpannableString(text);
        // when the white background span is applied, the text selection is not visible (only the non-spanned parts)
        Integer argb_back = Color.WHITE;

        String[] words_red = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "+", "-"};
        Integer argb_red = Color.RED;
        for (String word : words_red) {
            int substringStart=0;
            int start;
            while((start=text.indexOf(word,substringStart))>=0){
                spannable.setSpan(
                        new ForegroundColorSpan(argb_red),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
//                spannable.setSpan(
//                        new BackgroundColorSpan(argb_back),start,start+word.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                );
                substringStart = start+word.length();
            }
        }

        return spannable;
    }

    public static Spannable colorized_phreeqc(final String text) {
        SpannableString spannable = new SpannableString(text);
        Integer argb_back = Color.WHITE;

        String[] words_basic = {" ABS", " AND ", " AND\t", " ARCTAN", " ASC", " CEIL", " CHR$", " COS", " DATA ", " DATA\t", " DIM", " ERASE", " ELSE", " EOL$", " EXP", " FLOOR", " FOR ", " FOR\t", " NEXT ", " NEXT\t", " GOSUB ", " GOSUB\t", " GOTO ", " GOTO\t", " IF ", " IF\t", " INSTR", " LEN", " LOG", " LOG10", " LTRIM", " MID$", " MOD", " ON ", " ON\t", " OR ", " OR\t", " PAD", " READ ", " READ\t", " REM ", " REM\t", " RESTORE", " RETURN", " RTRIM", " SGN", " SIN", " SQR", " SQRT", " STR_E$", " STR_F$", " STR$", " STR_", " TAN", " TRIM", " VAL", " WEND ", " WEND\t", " WHILE ", " WHILE\t", " XOR", " abs", " and ", " and\t", " arctan", " asc", " ceil", " chr$", " cos", " data ", " data\t", " dim", " erase", " else", " eol$", " exp", " floor", " for ", " for\t", " next ", " next\t", " gosub ", " gosub\t", " goto ", " goto\t", " if ", " if\t", " instr", " len", " log", " log10", " ltrim", " mid$", " mod", " on ", " on\t", " or ", " or\t", " pad", " read ", " read\t", " rem ", " rem\t", " restore", " return", " rtrim", " sgn", " sin", " sqr", " sqrt", " str_e$", " str_f$", " str$", " str_", " tan", " trim", " val", " wend ", " wend\t", " while ", " while\t", " xor"};
        Integer argb_basic = Color.parseColor("#A52A2A");
        Integer argb_basic_back = Color.parseColor("#FFE797");
        for (String word : words_basic) {
            int substringStart=0;
            int start;
            while((start=text.indexOf(word,substringStart))>=0){
                spannable.setSpan(
                        new ForegroundColorSpan(argb_basic),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                spannable.setSpan(
                        new BackgroundColorSpan(argb_basic_back),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                substringStart = start+word.length();
            }
        }

        String[] words_brown = {"-description ", "-description\t", "-diff_c ", "-diff_c\t", "-gas", "-graph_sy ", "-graph_sy\t", "-graph_x ", "-graph_x\t", "-graph_y ", "-graph_y\t", "-lk_phase ", "-lk_phase\t", "-lk_species", "-misc1 ", "-misc1\t", "-misc2 ", "-misc2\t", "-osmotic", "-step_no ", "-step_no\t", "-total_time ", "-total_time\t", "ACT", "ALK ", "ALK\t", "APHI ", "APHI\t", "CELL_NO ", "CELL_NO\t", "CHANGE_POR", "CHANGE_SURF", "Color ", "Color\t", "CURRENT_A", "DESCRIPTION ", "DESCRIPTION\t", "DH_AV ", "DH_AV\t", "DIFF_C ", "DIFF_C\t", "DIST ", "DIST\t", "EDL", "EDL_SPECIES", "EOL$", "EPS_R", "EQUI", "EXISTS", "GAS", "GAS_P", "GAS_VM", "GET", "GET_POR", "GFW", "GRAPH_SY ", "GRAPH_SY\t", "GRAPH_X ", "GRAPH_X\t", "GRAPH_Y ", "GRAPH_Y\t", "KAPPA", "KIN", "KINETICS_FORMULA$", "KIN_DELTA", "KIN_TIME", "LA", "LG", "LIST_S_S", "LK_NAMED ", "LK_NAMED\t", "LK_PHASE ", "LK_PHASE\t", "LK_SPECIES", "LM", "MCD_JCONC", "MCD_JTOT", "MISC1", "MISC2", "MOL", "OSMOTIC", "PARM", "PHASE_FORMULA$", "PHASE_VM", "POT_V", "PR_P", "PR_PHI", "PUNCH ", "PUNCH\t", "PUT", "RHO", "RHO_0", "RXN", "SC", "SIM_NO", "SIM_TIME", "SOLN_VOL ", "SOLN_VOL\t", "SR ", "SR\t", "STEP_NO", "SUM_GAS", "SUM_S_S", "SUM_SPECIES", "SURF", "SYS", "S_S", "TC", "TK", "TOT", "TOTAL_TIME", "TOTMOLE", "VISCOS ", "VISCOS\t", "VISCOS_0 ", "VISCOS_0\t", "act", "alk", "aphi ", "aphi\t", "calc_value", "cell_no ", "cell_no\t", "change_por", "change_surf", "color ", "color\t", "current_a", "debye_length ", "debye_length\t", "delta_h_phase", "delta_h_species", "description ", "description\t", "dh_a0", "dh_bdot", "diff_c", "dist ", "dist\t", "edl", "edl_species", "eol$ ", "eol$\t", "eol_notab$ ", "eol_notab$\t", "eps_r ", "eps_r\t", "equi", "exists", "false", "gas", "gas_p ", "gas_p\t", "gas_vm", "get", "get_por", "gfw", "graph_sy ", "graph_sy\t", "graph_x ", "graph_x\t", "graph_y ", "graph_y\t", "kappa", "kin_delta", "kin_time", "kin", "kinetics_formula$ ", "kinetics_formula$\t", "la ", "la\t", "lg", "lk_phase ", "lk_phase\t", "lk_phase ", "lk_phase\t", "lk_species", "lm", "misc1 ", "misc1\t", "misc2 ", "misc2\t", "mol", "no_newlines ", "no_newlines\t", "osmotic", "parm", "phase_formula$", "phase_vm", "pot_v", "punch", "put ", "put\t", "qbrn", "rho", "rho_0", "rxn", "s_s", "sc", "sim_no ", "sim_no\t", "sim_time ", "sim_time\t", "soln_vol ", "soln_vol\t", "sr", "step_no ", "step_no\t", "sum_gas", "sum_s_s", "sum_species", "surf", "symbol ", "symbol\t", "symbol_size ", "symbol_size\t", "sys", "tc ", "tc\t", "tk ", "tk\t", "tot", "total_time ", "total_time\t", "totmole", "true", "viscos ", "viscos\t", "viscos_0 ", "viscos_0\t"};
        Integer argb_brown = Color.parseColor("#A52A2A");
        for (String word : words_brown) {
            int substringStart=0;
            int start;
            while((start=text.indexOf(word,substringStart))>=0){
                spannable.setSpan(
                        new ForegroundColorSpan(argb_brown),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
//                spannable.setSpan(
//                        new BackgroundColorSpan(argb_back),start,start+word.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                );
                substringStart = start+word.length();
            }
        }

        String[] words_gray = {"-alkalinity", "-all", "-append", "-isotope ", "-isotope\t", "-numerical_fixed_volume ", "-numerical_fixed_volume\t", "-solutions", "-start_time ", "-start_time\t", "Alkalinity ", "Alkalinity\t", "Brown ", "Brown\t", "Cyan ", "Cyan\t", "Lavender ", "Lavender\t", "LightSeaGreen ", "LightSeaGreen\t", "RoyalBlue ", "RoyalBlue\t", "Teal ", "Teal\t", "Tomato ", "Tomato\t", "alkalinity", "auto", "calculate", "charge", "closed", "constant", "flux ", "flux\t", "isotope ", "isotope\t", "numerical_fixed_volume ", "numerical_fixed_volume\t", "pH ", "pH\t", "pe ", "pe\t", "pitzer.dat", "set_diff_c", "solutions", "start_time ", "start_time\t", "sy_axis ", "sy_axis\t", "x_axis ", "x_axis\t", "y_axis ", "y_axis\t"};
        Integer argb_gray = Color.DKGRAY;
        for (String word : words_gray) {
            int substringStart=0;
            int start;
            while((start=text.indexOf(word,substringStart))>=0){
                spannable.setSpan(
                        new ForegroundColorSpan(argb_gray),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
//                spannable.setSpan(
//                        new BackgroundColorSpan(argb_back),start,start+word.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                );
                substringStart = start+word.length();
            }
        }

        String[] words_cyan = {"-ALPHAS ", "-ALPHAS\t", "-B0 ", "-B0\t", "-B1 ", "-B1\t", "-B2 ", "-B2\t", "-C0 ", "-C0\t", "-Eh ", "-Eh\t", "-Gugg_kJ ", "-Gugg_kJ\t", "-Gugg_nondim ", "-Gugg_nondim\t", "-LAMDA ", "-LAMDA\t", "-MacInnes ", "-MacInnes\t", "-Margules ", "-Margules\t", "-Millero ", "-Millero\t", "-PSI ", "-PSI\t", "-THETA ", "-THETA\t", "-ZETA ", "-ZETA\t", "-active", "-activities", "-activity_coefficients", "-activity_water", "-add_constant", "-add_logk", "-alyotropic_point", "-analytical_expression", "-axis_scale ", "-axis_scale\t", "-axis_titles ", "-axis_titles\t", "-bad_step_max ", "-bad_step_max\t", "-balances ", "-balances\t", "-bdot ", "-bdot\t", "-boundary_conditions ", "-boundary_conditions\t", "-capacitance", "-cd_music", "-cells ", "-cells\t", "-censor_mp", "-censor_species", "-charge_balance", "-chart_title ", "-chart_title\t", "-co2_coefs ", "-co2_coefs\t", "-co2_llnl_gamma ", "-co2_llnl_gamma\t", "-comp ", "-comp\t", "-comp1 ", "-comp1\t", "-comp2 ", "-comp2\t", "-connect_simulations ", "-connect_simulations\t", "-convergence_tolerance ", "-convergence_tolerance\t", "-correct_disp ", "-correct_disp\t", "-critical_point ", "-critical_point\t", "-cvode ", "-cvode\t", "-cvode_order ", "-cvode_order\t", "-cvode_steps ", "-cvode_steps\t", "-davies", "-debug_diffuse_layer ", "-debug_diffuse_layer\t", "-debug_inverse ", "-debug_inverse\t", "-debug_mass_action ", "-debug_mass_action\t", "-debug_mass_balance ", "-debug_mass_balance\t", "-debug_model ", "-debug_model\t", "-debug_prep ", "-debug_prep\t", "-debug_set ", "-debug_set\t", "-delta_h ", "-delta_h\t", "-density ", "-density\t", "-detach", "-diagonal_scale ", "-diagonal_scale\t", "-diffuse_layer ", "-diffuse_layer\t", "-diffusion_coefficient ", "-diffusion_coefficient\t", "-dispersivities ", "-dispersivities\t", "-distribution_coefficients", "-donnan ", "-donnan\t", "-dump_frequency ", "-dump_frequency\t", "-dump_restart ", "-dump_restart\t", "-dw ", "-dw\t", "-echo_input ", "-echo_input\t", "-epsilon ", "-epsilon\t", "-equilibrate ", "-equilibrate\t", "-erm_ddl ", "-erm_ddl\t", "-exchange_gammas ", "-exchange_gammas\t", "-file ", "-file\t", "-fix_current ", "-fix_current\t", "-fixed_pressure", "-fixed_volume", "-flow_direction ", "-flow_direction\t", "-force_equality", "-force_solutions", "-formula ", "-formula\t", "-gamma", "-gases", "-headings ", "-headings\t", "-high_precision ", "-high_precision\t", "-implicit ", "-implicit\t", "-initial_isotopes ", "-initial_isotopes\t", "-initial_solutions ", "-initial_solutions\t", "-initial_time ", "-initial_time\t", "-interlayer_d ", "-interlayer_d\t", "-ionic_strength ", "-ionic_strength\t", "-isotope_uncertainty ", "-isotope_uncertainty\t", "-iterations ", "-iterations\t", "-kinetic_reactants ", "-kinetic_reactants\t", "-lengths ", "-lengths\t", "-llnl_gamma ", "-llnl_gamma\t", "-log_k ", "-log_k\t", "-logfile ", "-logfile\t", "-lon_netpath ", "-lon_netpath\t", "-m ", "-m\t", "-m0 ", "-m0\t", "-mineral_water ", "-mineral_water\t", "-minimal ", "-minimal\t", "-miscibility_gap ", "-miscibility_gap\t", "-molalities ", "-molalities\t", "-mole_balance ", "-mole_balance\t", "-mp_tolerance ", "-mp_tolerance\t", "-multi_d ", "-multi_d\t", "-multiple_precision ", "-multiple_precision\t", "-new_line ", "-new_line\t", "-no_check ", "-no_check\t", "-no_edl ", "-no_edl\t", "-numerical_derivatives ", "-numerical_derivatives\t", "-omega", "-only_counter_ions ", "-only_counter_ions\t", "-other ", "-other\t", "-p_c", "-parms ", "-parms\t", "-pat_netpath", "-pe_step_size ", "-pe_step_size\t", "-percent_error", "-pitzer_exchange_gammas", "-plot_concentration_vs", "-plot_tsv_file ", "-plot_tsv_file\t", "-potential ", "-potential\t", "-pressure", "-print_cells", "-print_frequency", "-punch_cells", "-punch_frequency", "-porosities", "-range", "-redox", "-reset ", "-reset\t", "-runge_kutta ", "-runge_kutta\t", "-same_model ", "-same_model\t", "-saturation_indices ", "-saturation_indices\t", "-shifts ", "-shifts\t", "-simulation ", "-simulation\t", "-sites_units ", "-sites_units\t", "-species", "-spinodal_gap", "-stagnant ", "-stagnant\t", "-start", "-state", "-status ", "-status\t", "-steps ", "-steps\t", "-step_divide ", "-step_divide\t", "-step_size ", "-step_size\t", "-steps ", "-steps\t", "-t_c", "-temp ", "-temp\t", "-temperature ", "-temperature\t", "-tempk ", "-tempk\t", "-thermal_diffusion ", "-thermal_diffusion\t", "-time ", "-time\t", "-time_step ", "-time_step\t", "-tol ", "-tol\t", "-tolerance ", "-tolerance\t", "-totals ", "-totals\t", "-uncertainty ", "-uncertainty\t", "-uncertainty_water ", "-uncertainty_water\t", "-units ", "-units\t", "-use_etheta ", "-use_etheta\t", "-volume ", "-volume\t", "-viscosity ", "-viscosity\t", "-warnings ", "-warnings\t", "-water", "Black ", "Black\t", "Blue ", "Blue\t", "CHARGE_BALANCE", "Circle ", "Circle\t", "DH_A ", "DH_A\t", "DH_B ", "DH_B\t", "Diamond ", "Diamond\t", "GAMMA", "Green ", "Green\t", "HDash ", "HDash\t", "M ", "M\t", "M0 ", "M0\t", "MU", "Magenta ", "Magenta\t", "None ", "None\t", "Orange ", "Orange\t", "PERCENT_ERROR ", "PERCENT_ERROR\t", "PLOT_XY ", "PLOT_XY\t", "PRESSURE", "Plus ", "Plus\t", "Red ", "Red\t", "SI", "STEP ", "STEP\t", "Square ", "Square\t", "Star ", "Star\t", "THETA", "TIME ", "TIME\t", "Thompson ", "Thompson\t", "Triangle ", "Triangle\t", "TriangleDown ", "TriangleDown\t", "VDash ", "VDash\t", "XCross ", "XCross\t", "Yellow ", "Yellow\t", "ZETA ", "ZETA\t", "active ", "active\t", "activities", "activity_coefficients", "activity_water", "add_constant", "add_logk", "alyotropic_point", "analytical_expression", "axis_scale ", "axis_scale\t", "axis_titles ", "axis_titles\t", "backward", "bad_step_max ", "bad_step_max\t", "balances ", "balances\t", "bdot ", "bdot\t", "boundary_conditions ", "boundary_conditions\t", "capacitance", "cd_music", "cells ", "cells\t", "censor_mp", "censor_species", "charge_balance", "chart_title ", "chart_title\t", "co2_coefs ", "co2_coefs\t", "co2_llnl_gamma ", "co2_llnl_gamma\t", "comp ", "comp\t", "comp1 ", "comp1\t", "comp2 ", "comp2\t", "connect_simulations ", "connect_simulations\t", "convergence_tolerance ", "convergence_tolerance\t", "correct_disp ", "correct_disp\t", "correct_GC ", "correct_GC\t", "critical_point ", "critical_point\t", "cvode ", "cvode\t", "cvode_order ", "cvode_order\t", "cvode_steps ", "cvode_steps\t", "davies", "debug_diffuse_layer ", "debug_diffuse_layer\t", "debug_inverse ", "debug_inverse\t", "debug_mass_action ", "debug_mass_action\t", "debug_mass_balance ", "debug_mass_balance\t", "debug_model ", "debug_model\t", "debug_prep ", "debug_prep\t", "debug_set ", "debug_set\t", "delta_h ", "delta_h\t", "density", "detach", "diagonal_scale ", "diagonal_scale\t", "diffuse_layer ", "diffuse_layer\t", "diffusion_coefficient ", "diffusion_coefficient\t", "diffusion_only", "dispersivities ", "dispersivities\t", "distance ", "distance\t", "distribution_coefficients", "donnan ", "donnan\t", "dump_frequency ", "dump_frequency\t", "dump_restart ", "dump_restart\t", "dw ", "dw\t", "echo_input ", "echo_input\t", "equilibrate ", "equilibrate\t", "erm_ddl ", "erm_ddl\t", "exchange_gammas ", "exchange_gammas\t", "file ", "file\t", "fix_current ", "fix_current\t", "fixed_pressure", "fixed_volume", "flow_direction ", "flow_direction\t", "force_equality", "force_solutions", "formula ", "formula\t", "forward", "gamma", "gases", "headings ", "headings\t", "high_precision ", "high_precision\t", "implicit ", "implicit\t", "initial_isotopes ", "initial_isotopes\t", "initial_solutions ", "initial_solutions\t", "initial_time ", "initial_time\t", "interlayer_d ", "interlayer_d\t", "ionic_strength ", "ionic_strength\t", "isotope_uncertainty ", "isotope_uncertainty\t", "iterations ", "kinetic_reactants ", "kinetic_reactants\t", "lengths", "llnl_gamma ", "llnl_gamma\t", "log_k ", "log_k\t", "logfile ", "logfile\t", "lon_netpath ", "lon_netpath\t", "m ", "m\t", "m0 ", "m0\t", "mineral_water ", "mineral_water\t", "minimal ", "minimal\t", "miscibility_gap ", "miscibility_gap\t", "molalities ", "molalities\t", "mole_balance ", "mole_balance\t", "mp_tolerance ", "mp_tolerance\t", "mu", "multi_d ", "multi_d\t", "multiple_precision ", "multiple_precision\t", "new_line ", "new_line\t", "no_check", "no_edl ", "no_edl\t", "numerical_derivatives ", "numerical_derivatives\t", "only_counter_ions ", "only_counter_ions\t", "other ", "other\t", "p_c", "parms ", "parms\t", "pat_netpath", "pe_step_size ", "pe_step_size\t", "percent_error", "pitzer_exchange_gammas", "plot_concentration_vs ", "plot_concentration_vs\t", "plot_csv_file ", "plot_csv_file\t", "plot_xy ", "plot_xy\t", "potential ", "potential\t", "pressure", "print_cells", "print_frequency ", "print_frequency\t", "punch_cells", "punch_frequency ", "punch_frequency\t", "porosities", "range", "redox", "reset ", "reset\t", "runge_kutta ", "runge_kutta\t", "same_model ", "same_model\t", "saturation_indices ", "saturation_indices\t", "shifts ", "shifts\t", "si ", "si\t", "simulation", "sites_units", "species", "spinodal_gap", "stagnant ", "stagnant\t", "start", "state", "status", "steps ", "steps\t", "step_divide ", "step_divide\t", "step_size ", "step_size\t", "steps ", "steps\t", "t_c ", "t_c\t", "temp ", "temp\t", "temperature ", "temperature\t", "tempk ", "tempk\t", "thermal_diffusion ", "thermal_diffusion\t", "time ", "time\t", "time_step ", "time_step\t", "tol ", "tol\t", "tolerance ", "tolerance\t", "totals ", "totals\t", "uncertainty", "uncertainty_water", "units ", "units\t", "use_etheta ", "use_etheta\t", "vm", "volume", "warnings ", "warnings\t", "water"};
        Integer argb_cyan = Color.parseColor("#7AD5E1");
        for (String word : words_cyan) {
            int substringStart=0;
            int start;
            while((start=text.indexOf(word,substringStart))>=0){
                spannable.setSpan(
                        new ForegroundColorSpan(argb_cyan),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                spannable.setSpan(
                        new android.text.style.StyleSpan(Typeface.BOLD),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
//                spannable.setSpan(
//                        new BackgroundColorSpan(argb_back),start,start+word.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                );
                substringStart = start+word.length();
            }
        }

        String[] words_blue = {"GAS_BINARY_PARAMETERS", "MEAN_GAMMAS", "ADVECTION", "CALCULATE_VALUES", "COPY", "DATABASE", "DELETE", "DUMP", "END", "EQUILIBRIUM_PHASES", "EXCHANGE", "EXCHANGE_MASTER_SPECIES", "EXCHANGE_SPECIES", "GAS_PHASE", "INCLUDE$", "INCREMENTAL_REACTIONS", "INVERSE_MODELING", "ISOTOPES", "ISOTOPE_ALPHAS", "ISOTOPE_RATIOS", "KINETICS", "KNOBS", "LLNL_AQUEOUS_MODEL_PARAMETERS", "MIX", "NAMED_EXPRESSIONS", "PHASES", "PITZER", "PRINT", "RATES", "REACTION", "REACTION_PRESSURE", "REACTION_TEMPERATURE", "RUN_CELLS", "SAVE", "SELECTED_OUTPUT", "SIT", "SOLID_SOLUTIONS", "SOLUTION", "SOLUTION ", "SOLUTION_MASTER_SPECIES", "SOLUTION_SPECIES", "SOLUTION_SPREAD", "SURFACE", "SURFACE_MASTER_SPECIES", "SURFACE_SPECIES", "TITLE", "TRANSPORT", "USE", "USER_GRAPH", "USER_PRINT", "USER_PUNCH", "EQUILIBRIUM_PHASES_MODIFY", "EXCHANGE_MODIFY", "GAS_PHASE_MODIFY", "KINETICS_MODIFY", "REACTION_MODIFY", "SOLID_SOLUTIONS_MODIFY", "SOLUTION_MODIFY", "SURFACE_MODIFY", "-calculate_values", "-dump ", "-dump\t", "-end ", "-end\t", "-equilibrium_phases ", "-equilibrium_phases\t", "-exchange ", "-exchange\t", "-gas_phase ", "-gas_phase\t", "-inverse_modeling ", "-inverse_modeling\t", "-isotope_alphas ", "-isotope_alphas\t", "-isotope_ratios ", "-isotope_ratios\t", "-isotopes ", "-isotopes\t", "-kinetics ", "-kinetics\t", "-phases", "-reaction ", "-reaction\t", "-reaction_pressure ", "-reaction_pressure\t", "-reaction_temperature ", "-reaction_temperature\t", "-solid_solutions ", "-solid_solutions\t", "-solution ", "-solution\t", "-surface", "-user_graph ", "-user_graph\t", "-user_print ", "-user_print\t", "-user_punch ", "-user_punch\t", "DUMP", "KINETICS", "KINETICS_MIX", "KINETICS_MODIFY", "LLNL_AQUEOUS_MODEL_PARAMETERS", "PRINT", "SIT", "calculate_values", "dump ", "dump\t", "end ", "end\t", "equilibrium_phases ", "equilibrium_phases\t", "exchange ", "exchange\t", "gas_phase ", "gas_phase\t", "inverse_modeling ", "inverse_modeling\t", "isotope_alphas ", "isotope_alphas\t", "isotope_ratios ", "isotope_ratios\t", "isotopes ", "isotopes\t", "kinetics ", "kinetics\t", "phases", "print", "reaction ", "reaction\t", "reaction_pressure ", "reaction_pressure\t", "reaction_temperature ", "reaction_temperature\t", "save", "solid_solutions ", "solid_solutions\t", "solution ", "solution\t", "surface", "user_graph", "user_print", "user_punch"};
        Integer argb_blue = Color.BLUE;
        for (String word : words_blue) {
            int substringStart=0;
            int start;
            while((start=text.indexOf(word,substringStart))>=0){
                spannable.setSpan(
                        new ForegroundColorSpan(argb_blue),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
//                spannable.setSpan(
//                        new BackgroundColorSpan(argb_back),start,start+word.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                );
                substringStart = start+word.length();
            }
        }

        String[] words_red = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "+", "-"};
        Integer argb_red = Color.RED;
        for (String word : words_red) {
            int substringStart=0;
            int start;
            while((start=text.indexOf(word,substringStart))>=0){
                spannable.setSpan(
                        new ForegroundColorSpan(argb_red),start,start+word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
//                spannable.setSpan(
//                        new BackgroundColorSpan(argb_back),start,start+word.length(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                );
                substringStart = start+word.length();
            }
        }

        // currently not working - not perfect analogy with words_comments
        //
//        String[] words_quotations = {"\'"};
//        Integer argb_quotations = Color.MAGENTA;
//        for (String word : words_quotations) {
//            int substringStart = 0;
//            int start;
//            int i = 0;
//            int prevQuot = 0;
//            int nextQuot = 0;
//            while ((start = text.indexOf(word, substringStart)) >= 0) {
//                i++;
//                if (i == 1){
//                    prevQuot = text.indexOf(word,start);
//                    substringStart = prevQuot;
//                } else if (i > 1){
//                    nextQuot = text.indexOf(word,prevQuot);
//                    if (nextQuot != -1) {
//                        spannable.setSpan(
//                                new ForegroundColorSpan(argb_quotations), prevQuot, nextQuot,
//                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                        );
//                        substringStart = nextQuot+1;
//                        prevQuot = nextQuot+1;
//                    } else {
//                        spannable.setSpan(
//                                new ForegroundColorSpan(argb_quotations), prevQuot, text.length(),
//                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                        );
//                        substringStart = start+word.length();
//                    }
//                }
//            }
//        }

        String[] words_quotations1 = {"\\'(.*?)\\'"};
        Integer argb_quotations1 = Color.MAGENTA;
        for (String word : words_quotations1) {
            int substringStart = 0;
            int start;
            while ((start = text.indexOf(word, substringStart)) >= 0) {
                int neqtQuot1 = text.indexOf(word,start);
                // endOfLine = -1 in the last line, since there is no more line break until the ond of the file
                if (neqtQuot1 != -1) {
                    spannable.setSpan(
                            new ForegroundColorSpan(argb_quotations1), start, neqtQuot1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
//                    spannable.setSpan(
//                            new BackgroundColorSpan(argb_back),start,endOfLine,
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                                );
                    substringStart = neqtQuot1;
                } else {
                    spannable.setSpan(
                            // in the last line, only the "#" character would be colorized by green, the following characters would be either black or colorized by previous
//                            new ForegroundColorSpan(argb_comment), start, start+word.length(),
                            // text.length() is the position of the end of whole text, better (it works as should)
                            new ForegroundColorSpan(argb_quotations1), start, text.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
//                    spannable.setSpan(
//                            new BackgroundColorSpan(argb_back),start,text.length(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                                );
                    substringStart = start+word.length();
                }
            }
        }

        String[] words_comment = {"#", "REM"};
        Integer argb_comment = Color.parseColor("#058a47");
        for (String word : words_comment) {
            int substringStart = 0;
            int start;
            while ((start = text.indexOf(word, substringStart)) >= 0) {
                int endOfLine = text.indexOf("\n",start);
                // endOfLine = -1 in the last line, since there is no more line break until the ond of the file
                if (endOfLine != -1) {
                    spannable.setSpan(
                            new ForegroundColorSpan(argb_comment), start, endOfLine,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
//                    spannable.setSpan(
//                            new BackgroundColorSpan(argb_back),start,endOfLine,
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                                );
                    substringStart = endOfLine;
                } else {
                    spannable.setSpan(
                            // in the last line, only the "#" character would be colorized by green, the following characters would be either black or colorized by previous
//                            new ForegroundColorSpan(argb_comment), start, start+word.length(),
                            // text.length() is the position of the end of whole text, better (it works as should)
                            new ForegroundColorSpan(argb_comment), start, text.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
//                    spannable.setSpan(
//                            new BackgroundColorSpan(argb_back),start,text.length(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                                );
                    substringStart = start+word.length();
                }
            }
        }

        return spannable;
    }

}
