package org.facengineer.WebPage;

import org.facengineer.DaoMapper.RtcTrelloDao;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rtctrello")
public class B_RtcTrello {
    private RtcTrelloDao rtcTrelloDao;

    B_RtcTrello(RtcTrelloDao rtcTrelloDao){this.rtcTrelloDao = rtcTrelloDao;}

    @RequestMapping(value="/post/", method = RequestMethod.POST)
    public String RtcTrelloPostFunc(@RequestParam(value = "rtcname", required = true) String rtcname,
                                    @RequestParam(value = "trelloname", required = true) String trelloname){
        System.out.println(rtcname + " " + trelloname);
        return "SUCCEED";
    }
}
