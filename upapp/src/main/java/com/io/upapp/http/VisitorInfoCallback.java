package com.io.upapp.http;

import com.io.upapp.http.model.VisitorModel;

public interface VisitorInfoCallback {
   void onSuccess(VisitorModel visitorInfo);

    void onError(String err);
}
