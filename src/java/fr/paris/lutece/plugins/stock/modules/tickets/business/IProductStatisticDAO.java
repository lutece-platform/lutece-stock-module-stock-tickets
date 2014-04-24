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
package fr.paris.lutece.plugins.stock.modules.tickets.business;

import fr.paris.lutece.util.jpa.IGenericDAO;

import java.util.List;


/**
 * IPersonDAO Interface
 */
public interface IProductStatisticDAO extends IGenericDAO<Integer, ProductStatistic>
{
    /**
     * get all ProductStatistic by the product identifier
     * @param idProduct the identifier of product
     * @return a list of ProductStatistic instance
     */
    List<ProductStatistic> getAllByIdProduct( Integer idProduct );

    /**
     * get all association of date and products count by parameters
     * @param strTimesUnit the times unit
     * @param strDateDebut the begin date
     * @param strDateFin the end date
     * @return a list of ResultStatistic
     */
    List<ResultStatistic> getAllResultStatisticByParameters( String strTimesUnit, String strDateDebut, String strDateFin );

    /**
     * get the purchase count by dates parameters
     * @param strDateDebut the date begin
     * @param strDateFin the date end
     * @return the purchase count
     */
    Integer getCountProductsByDates( String strDateDebut, String strDateFin );
}
