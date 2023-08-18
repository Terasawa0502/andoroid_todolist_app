package com.example.todolistapp.di;

import android.content.Context;

import com.example.todolistapp.data.repositories.ToDoListRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;

@Module
@InstallIn(ViewModelComponent.class)
final class ViewModelModule {
    @Provides
    @ViewModelScoped
    static ToDoListRepository provideRepository (@ApplicationContext Context context) {
        return new ToDoListRepository(context);
    }
}
