package com.androidhuman.rxfirebase.auth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.support.annotation.NonNull;

import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class SignInAnonymousOnSubscribe implements SingleOnSubscribe<FirebaseUser> {

    private final FirebaseAuth instance;

    SignInAnonymousOnSubscribe(FirebaseAuth instance) {
        this.instance = instance;
    }

    @Override
    public void subscribe(final SingleEmitter<FirebaseUser> emitter) {
        final OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(task.getException());
                    }
                    return;
                }

                if (!emitter.isDisposed()) {
                    emitter.onSuccess(task.getResult().getUser());
                }
            }
        };

        instance.signInAnonymously()
                .addOnCompleteListener(listener);
    }
}
