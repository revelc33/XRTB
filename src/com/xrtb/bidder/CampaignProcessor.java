package com.xrtb.bidder;

import java.util.UUID;
import java.util.concurrent.Callable;



import com.xrtb.common.Campaign;
import com.xrtb.common.Creative;
import com.xrtb.common.Node;
import com.xrtb.pojo.BidRequest;
import com.xrtb.pojo.BidResponse;

/**
 * CampaignProcessor. 
 * Given a campaign, process it into a bid.
 * @author Ben M. Faul
 *
 */
public class CampaignProcessor implements Callable<BidResponse> {
	/** The campaign used by this processor object */
	Campaign camp;
	
	/** The bid request that will be used by this processor object */
	BidRequest br;
	
	/** The response that will be created from the processing of the request with the campaign. */
	BidResponse response;
	
	/** The unique ID assigned to the bid response. This is probably not needed TODO: Need to remove this */
	UUID uuid = UUID.randomUUID();
	/**
	 * Constructor.
	 * @param camp Campaign. The campaign to process
	 * @param br. BidRequest. The bid request to apply to this campaign.
	 */
	public CampaignProcessor(Campaign camp, BidRequest br) {
		this.camp = camp;
		this.br = br;
	}
	
	/**
	 * Return the bid if the campaign can bid on the request.
	 * TODO: Is it really necessary to use yet another generated OID when the exchange already gives you one?
	 */
	@Override
	public BidResponse call() throws Exception {
		Creative selectedCreative = null;
		for (Creative create : camp.creatives) {
			if (br.w == create.w && br.h == create.h)
				selectedCreative = create;
		}
		if (selectedCreative == null)
			return null;
		
		for (int i=0;i<camp.nodes.size();i++) {
			Node n = camp.nodes.get(i);
			if (n.test(br) == false)
				return null;
		}
		BidResponse response = new BidResponse(br,camp,selectedCreative,br.id /*uuid.toString()*/);
		response.forwardUrl = selectedCreative.forwardUrl;
		response.adm = camp.template;
		response.makeResponse();
		return response;
	}
	
}
