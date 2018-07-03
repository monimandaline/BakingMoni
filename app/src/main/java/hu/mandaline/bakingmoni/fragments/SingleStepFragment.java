package hu.mandaline.bakingmoni.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hu.mandaline.bakingmoni.R;
import hu.mandaline.bakingmoni.model.Step_model;

// A simple {@link Fragment} subclass.
public class SingleStepFragment extends Fragment {

    private ArrayList<Step_model> steps;
    private Bundle args;
    private TextView stepDescription;
    private Step_model step = new Step_model();
    private int listIndex;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private View rootView;
    private ImageView thumbnailView;
    Uri videoUri;
    boolean playWhenReady;
    long videoPosition = 0;
    int currentWindow = 0;


    public SingleStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_single_step, container, false);
        stepDescription = rootView.findViewById(R.id.tv_step);
        exoPlayerView = rootView.findViewById(R.id.exoplayer_view);
        thumbnailView = rootView.findViewById(R.id.iv_step);

        // Load the saved state
        if (savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList("SSteps");
            videoPosition = savedInstanceState.getLong("SVideoPositon");
            playWhenReady = savedInstanceState.getBoolean("SPlayWhenReady");
            listIndex = savedInstanceState.getInt("SListIndex");
            updateStepView(listIndex);

        } else {
            args = getArguments();
            if (getArguments() != null) {
                steps = args.getParcelableArrayList("StepsForFragment");
                listIndex = args.getInt("listIndex");
                updateStepView(listIndex);
            }
        }
        return rootView;
    }

    public void updateStepView(int position) {

        step = steps.get(position);
        stepDescription.setText(step.getDescription());

        if (step.getVideoURL().equals("")) {
            exoPlayerView.setVisibility(View.GONE);
            thumbnailView.setVisibility(View.VISIBLE);
            Context context = rootView.getContext();
            if (step.getThumbnailURL().equals("")) {
                thumbnailView.setImageDrawable(context.getResources().getDrawable(R.drawable.dummycake0));
            } else {
                Picasso.with(context)
                        .load(step.getThumbnailURL())
                        .placeholder(context.getResources().getDrawable(R.drawable.dummycake0))
                        .error(context.getResources().getDrawable(R.drawable.dummycake0))
                        .into(thumbnailView);
            }
        } else {
            exoPlayerView.setVisibility(View.VISIBLE);
            thumbnailView.setVisibility(View.GONE);
            videoUri = Uri.parse(step.getVideoURL());
           initializePlayer(videoUri);
        }
    }

    // Initialize ExoPlayer.
    public void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(rootView.getContext(), trackSelector, loadControl);
            exoPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(rootView.getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    rootView.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(currentWindow, videoPosition);
            mExoPlayer.setPlayWhenReady(playWhenReady);

        }
    }

    // Release ExoPlayer
    private void releasePlayer() {
        videoPosition = mExoPlayer.getCurrentPosition();
        playWhenReady = mExoPlayer.getPlayWhenReady();
        currentWindow = mExoPlayer.getCurrentWindowIndex();
        mExoPlayer.stop();
        mExoPlayer.release();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        mExoPlayer = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentWindow == 0) {
            initializePlayer(videoUri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        if (mExoPlayer != null) {
            currentState.putLong("SVideoPositon", videoPosition);
            currentState.putBoolean("SPlayWhenReady", playWhenReady);
            currentState.putParcelableArrayList("SSteps", steps);
            // current step
            currentState.putInt("SListIndex", listIndex);

        }
    }

}
