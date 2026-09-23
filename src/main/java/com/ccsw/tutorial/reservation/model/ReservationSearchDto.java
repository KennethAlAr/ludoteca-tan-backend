package com.ccsw.tutorial.reservation.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

/**
 * @author ccsw
 *
 */
public class ReservationSearchDto {

    private PageableRequest pageable;

    public PageableRequest getPageable() { return pageable; }

    public void setPageable(PageableRequest pageable) { this.pageable = pageable; }
}
