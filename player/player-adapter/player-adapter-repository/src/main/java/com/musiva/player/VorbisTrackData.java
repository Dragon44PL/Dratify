package com.musiva.player;

import org.gagravarr.vorbis.VorbisAudioData;
import org.gagravarr.vorbis.VorbisComments;
import org.gagravarr.vorbis.VorbisInfo;
import org.gagravarr.vorbis.VorbisSetup;

import java.util.List;

public record VorbisTrackData(List<VorbisAudioData> vorbisAudioData, VorbisInfo vorbisInfo, VorbisComments vorbisComments, VorbisSetup vorbisSetup) { }
