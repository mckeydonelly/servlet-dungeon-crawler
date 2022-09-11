package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.phrase.Phrase;
import lombok.Data;

import java.util.Map;

@Data
public class Dialog {
    private String id;
    private String greeting;
    private Map<Integer, Phrase> phrases;
}
