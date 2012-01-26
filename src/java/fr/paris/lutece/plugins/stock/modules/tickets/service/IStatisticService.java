/*
 * Copyright (c) 2002-2008, Mairie de Paris
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
package fr.paris.lutece.plugins.stock.modules.tickets.service;

import fr.paris.lutece.plugins.stock.modules.tickets.business.ReservationDTO;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ResultStatistic;
import fr.paris.lutece.plugins.stock.modules.tickets.business.ShowDTO;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;


/**
 * The Interface IStatisticService.
 */
@Transactional
public interface IStatisticService
{
    /**
     * Modifies a productStatistic
     * @param product
     *            The product of the productStatistic to modify in database
     */
    void doManageProductSaving( ShowDTO product );

    /**
     * get a list of ResultStatistic containing all associations of date and
     * number of product by parameters
     * @param strTimesUnit the time unit
     * @param strDateDebut the date begin
     * @param strDateFin teh date end
     * @return the list of ResultStatistic
     */
    List<ResultStatistic> getProductStatistic( String strTimesUnit, String strDateDebut, String strDateFin );

    /**
     * get the products count by dates parameters
     * @param strDateDebut the date begin
     * @param strDateFin teh date end
     * @return the product count
     */
    Integer getCountProductsByDates( String strDateDebut, String strDateFin );

    /**
     * Modifies a purchaseStatistic
     * @param purchaseDTO
     *            The purchase of the purchaseStatistic to modify in database
     */
    void doManagePurchaseSaving( ReservationDTO purchaseDTO );

    /**
     * get a list of ResultStatistic containing all associations of date and
     * number of purchase by parameters
     * @param strTimesUnit the time unit
     * @param strDateDebut the date begin
     * @param strDateFin teh date end
     * @return the list of ResultStatistic
     */
    List<ResultStatistic> getPurchaseStatistic( String strTimesUnit, String strDateDebut, String strDateFin );

    /**
     * get the purchase count by dates parameters.
     * 
     * @param strDateDebut the date begin
     * @param strDateFin the date end
     * @return the count purchases by dates
     */
    Integer getCountPurchasesByDates( String strDateDebut, String strDateFin );

    /**
     * get the product showing count
     * @return the product count
     */
    Integer getCountProductALAffiche( );

    /**
     * get the product forthcoming count
     * @return the product count
     */
    Integer getCountProductAVenir( );

    /**
     * get the purchase day count
     * @return the purchase count
     */
    Integer getCountPurchaseOfDay( );

    /**
     * get the purchase month count
     * @return the purchase count
     */
    Integer getCountPurchaseOfMonth( );

    /**
     * remove all product statistic associated to the product identifier
     * @param nIdProduct the identifier of the product
     */
    void doRemoveProductStatisticByIdProduct( Integer nIdProduct );

    /**
     * remove all purchase statistic associated to the purchase identifier
     * @param nIdPurchase the identifier of the product
     */
    void doRemovePurchaseStatisticByIdPurchase( Integer nIdPurchase );
}
