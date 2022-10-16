package com.example.lifehack.presentation.adapter.intreface

import com.example.lifehack.data.entity.Follow.Data

interface OnClickFollower {

    fun onClickFollower(data: Data, delete: Boolean)
}