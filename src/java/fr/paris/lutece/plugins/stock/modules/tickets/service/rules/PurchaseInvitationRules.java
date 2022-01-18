/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
package fr.paris.lutece.plugins.stock.modules.tickets.service.rules;

import fr.paris.lutece.plugins.stock.business.offer.Offer;
import fr.paris.lutece.plugins.stock.business.purchase.IPurchaseDTO;
import fr.paris.lutece.plugins.stock.business.purchase.exception.PurchaseOutOfStock;
import fr.paris.lutece.plugins.stock.business.purchase.exception.PurchaseSessionExpired;
import fr.paris.lutece.plugins.stock.commons.exception.BusinessException;
import fr.paris.lutece.plugins.stock.modules.tickets.service.IPurchaseService;
import fr.paris.lutece.plugins.stock.service.IOfferService;
import fr.paris.lutece.plugins.stock.service.PurchaseRules;
import fr.paris.lutece.portal.service.util.AppLogService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Business rules for offer type Invitation
 *
 * @author abataille
 */
public class PurchaseInvitationRules extends PurchaseRules
{
    public static final String MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER_TYPE = "module.stock.billetterie.message.error.purchase.quantity.offer.type";
    @Inject
    private IPurchaseService _purchaseService;
    @Inject
    @Named( "stock-tickets.seanceService" )
    private IOfferService _offerService;

    /*
     * (non-Javadoc)
     * 
     * @see fr.paris.lutece.plugins.stock.service.IPurchaseRules#checkBeforePurchase (fr.paris.lutece.plugins.stock.business.purchase.Purchase)
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkBeforePurchase( IPurchaseDTO purchase, String sessionId ) throws PurchaseOutOfStock, PurchaseSessionExpired
    {
        super.checkBeforePurchase( purchase, sessionId );

        AppLogService.debug( "Vérification des règles de gestion pour une invitation, SID = {}", sessionId );

        Offer offer = _offerService.findById( purchase.getOfferId( ) );
        Integer nbReservation = _purchaseService.getNumberOfReservationByIdProductAndUserName( offer.getProduct( ).getId( ), offer.getType( ).getId( ),
                purchase.getUserName( ) );

        if ( ( purchase.getQuantity( ) + nbReservation ) > offer.getMaxTickets( ) )
        {
            throw new BusinessException( purchase, MESSAGE_ERROR_PURCHASE_QUANTITY_OFFER_TYPE );
        }

        AppLogService.debug( "Vérification ok, SID = {}", sessionId );
    }
}
