/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.stock.modules.tickets.utils.constants;


/**
 * The Class TicketsConstants.
 */
public class TicketsConstants
{
    public static final String INDENT_STRING = "-";
    public static final String UNDERSCORE_STRING = "_";
    public static final String SPACE_STRING = " ";
    public static final String DOT_STRING = ".";
    public static final String COLON_STRING = ":";
    public static final String COMMA_STRING = ",";
    public static final String NUMBER_ONE_STRING = "1";
    public static final String TRUE_STRING = "true";
    public static final String ZERO_STRING = "0";

    //Filters
    public static final String PARAMETER_FILTER = "filter";
    public static final String PARAMETER_FILTER_NAME = "filter_name";

    // Marks
    public static final String MARK_PAGINATOR = "paginator";
    public static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    public static final String MARK_FILTER = "filter";

    // Properties
    public static final String PROPERTY_DEFAULT_ITEM_PER_PAGE = "stock-kiosque.itemsPerPage";
    public static final String PROPERTY_CSV_SEPARATOR = "stock-billetterie.csv.separator";
    public static final String PROPERTY_CSV_EXTENSION = "stock-billetterie.csv.extension";
    public static final String PROPERTY_CSV_PURCHASE_NAME = "stock-billetterie.csv.purchase.file.name";
    public static final String I18N_ALL = "module.stock.kiosque.manage_partner.filter.defaultType";

    // Parameters
    public static final String PARAMETER_BUTTON_REFRESH = "refresh";
    public static final String PARAMETER_QUANTITY = "quantity";
    public static final String PARAMETER_FIRST_NAME = "firstName";
    public static final String PARAMETER_LAST_NAME = "lastName";
    public static final String PARAMETER_ERROR = "error";

    // Values
    public static final int UNLIMITED_QUANTITY_VALUE = -1;
    public static final String OFFER_STATUT_CANCEL = "annule";
    public static final String OFFER_STATUT_LOCK = "verrouille";
    public static final String OFFER_STATUT_OPEN = "";
    public static final Integer OFFER_TYPE_REDUCT_ID = 1;
    public static final String OFFER_TYPE_REDUCT = "Tarif réduit";
    public static final String OFFER_TYPE_INVITATION = "Invitation";
    public static final String OFFER_TYPE_INVITATION_SPECTACLE_ENFANT = "Invitation spectacle enfants";

    // Errors
    public static final String ERROR_BOOKING = "error%";
    public static final String NO_ERROR_BOOKING = "no_error%";

    // Attributes
    public static final String ATTR_POSTER_NAME = "posterName";
    public static final String FORMAT_COMBO_DATE_SEANCE = "dd MMMMM yyyy '-' HH'h'mm";
}
