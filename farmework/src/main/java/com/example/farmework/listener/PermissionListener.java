package com.example.farmework.listener;

import java.util.List;

public interface PermissionListener {
    void Granted();

    void onDeied(List<String> deniedPermissions);
}
